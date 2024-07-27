package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.exception.ManageExam.AttemptInvalidException;
import com.krs.knowledgerevisingsystem.exception.ManageExam.ExamInvalidException;
import com.krs.knowledgerevisingsystem.exception.ManageUser.UserInvalidException;
import com.krs.knowledgerevisingsystem.repository.AttemptRepository;
import com.krs.knowledgerevisingsystem.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class ExamController {
    private final AttemptService attemptService;
    private final ExamService examService;
    private final UserService userService;
    private final LessonExamService lessonExamService;
    private final CourseService courseService;
    private final LessonService lessonService;
    private final SubjectService subjectService;

    private User user;

    public ExamController(CourseService courseService, AttemptService attemptService, ExamService examService, UserService userService, LessonExamService lessonExamService, AttemptRepository attemptRepository, LessonService lessonService, SubjectService subjectService) {
        this.attemptService = attemptService;
        this.examService = examService;
        this.userService = userService;
        this.lessonExamService = lessonExamService;
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.subjectService = subjectService;
    }


    @PostMapping("/exam/add")
    public String handleExamCreation(HttpServletRequest request,
                                     @RequestParam("examName") String examName,
                                     @RequestParam("examDescription") String examDescription,
                                     @RequestParam("examLength") Long examLength,
                                     @RequestParam("courseId") Long courseId) {

        System.out.println(examName + " " + examDescription + " " + examLength + " " + courseId);
        System.out.println(request.getParameterMap());
        // Check for null parameters and return an error if any are missing
        if (examName == null || examDescription == null || examLength == null || courseId == null) {
            return "redirect:/error";
        }

        // Find the course by ID and return an error if not found
        Optional<Course> courseOptional = courseService.findById(courseId);
        if (!courseOptional.isPresent()) {
            return "redirect:/error";
        }
        Course course = courseOptional.get();
        System.out.println(course.getCourseName());

        // Initialize a map to store lesson IDs and their corresponding question counts
        Map<Long, Short> lessonQuestionsMap = new HashMap<>();

        // Process request parameters to populate the map
        request.getParameterMap().forEach((key, value) -> {
            if (key.startsWith("numQuestions")) {
                String lessonIdStr = key.replace("numQuestions", "");
                Long lessonId = Long.valueOf(lessonIdStr);
                short numQuestions = value[0].isEmpty() ? 0 : Integer.valueOf(value[0]).shortValue();
                lessonQuestionsMap.put(lessonId, numQuestions);
            }
        });

        // Create the exam object and set its properties
        Exam exam = new Exam();
        exam.setName(examName);
        exam.setDescription(examDescription);
        exam.setLength(examLength);
        exam.setCourse(course);

        // Optionally save the exam object to the database here
        examService.saveExam(exam);

        // Process each lesson and its corresponding question count
        lessonQuestionsMap.forEach((lessonId, numQuestions) -> {
            if (numQuestions > 0) {
                Lesson lesson = lessonService.findLessonById(lessonId);

                LessonExam examQuestion = new LessonExam();
                examQuestion.setExam(exam);
                examQuestion.setLesson(lesson);
                examQuestion.setNumberOfQuestions(numQuestions);
                // Optionally save the examQuestion object to the database here
                lessonExamService.saveLessonExam(examQuestion);
            }
        });

        // Redirect to a success page or another relevant page
        return "redirect:/exam=" + exam.getId();
    }

    @RequestMapping("/exam={examId}")
    public ModelAndView viewExam(Principal principal, @PathVariable Long examId) throws UserInvalidException, ExamInvalidException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exam");
        Exam exam = examService.findById(examId);
        if (exam == null) {
            throw new ExamInvalidException("Exam not found");
        }
        modelAndView.addObject("exam", exam);
        user = userService.findByUser(principal.getName());
        if (user == null) {
            throw new UserInvalidException("User not found");
        }

        if(exam.getCourse()!=null)
        {
            if(user.getRole().getName().contains("ADMIN"))
            {

            }
            else if (exam.getCourse().getEnrollments().stream().noneMatch(enrollment -> enrollment.getUser().getId().equals(user.getId()))) {
                throw new UserInvalidException("User not enrolled in course");
            }
        } else if (!exam.getCreator().equals(user)&&exam.isStatus()) {
            throw new UserInvalidException("User not enrolled in course");
        }
        if(Objects.equals(user.getId(), exam.getCreator().getId())||user.getRole().getName().contains("ADMIN")){
            modelAndView.addObject("creator", true);
        }
        //List<Attempt> attempts = attemptService.findByUserAndExam(user.getId(), examId);
        List<Attempt> attempts = attemptService.findByUserExam(exam, user);
        modelAndView.addObject("attempts", attempts);
        String btnContent = "Attempt";
        String btnHref = "/exam=" + examId + "/new_attempt";
        for (Attempt attempt : attempts) {
            if (attempt.isStatus()) {
                btnContent = "Continue Attempt";
                btnHref = "/exam=" + examId + "/attempt=" + attempt.getId();
            }
        }
        modelAndView.addObject("btnContent", btnContent);
        modelAndView.addObject("btnHref", btnHref);
        return modelAndView;
    }

    @RequestMapping("/exam={examId}/attempt={attemptId}")
    public ModelAndView viewAttempt(Principal principal, @PathVariable Long attemptId, @PathVariable Long examId) throws UserInvalidException, ExamInvalidException, AttemptInvalidException {
        Exam exam = examService.findById(examId);
        if (exam == null) {
            throw new ExamInvalidException("Exam not found");
        }
        user = userService.findByUser(principal.getName());
        if (user == null) {
            throw new UserInvalidException("User not found");
        }
        Attempt attempt = attemptService.findByAttemptId(attemptId);
        if (attempt == null) {
            throw new AttemptInvalidException("Attempt not found");
        }
        if(attempt.getUser()!=user){
            throw new UserInvalidException("Wrong user");
        }
        if (attempt.isStatus()&&attempt.getAttemptDate().getTime() + attempt.getExam().getLength() * 60000 > System.currentTimeMillis()) {
            return continueAttempt(attempt);
        }
        exam.setStatus(false);
        attemptService.saveAttempt(attempt);
        return viewOldAttempt(attempt);
    }

    @GetMapping("/exam={examId}/new_attempt")
    public ModelAndView newAttempt(Principal principal, @PathVariable Long examId) throws UserInvalidException, ExamInvalidException {
        Exam exam = examService.findById(examId);
        if (exam == null) {
            throw new ExamInvalidException("Exam not found");
        }
        user = userService.findByUser(principal.getName());
        if (user == null) {
            throw new UserInvalidException("User not found");
        }

        Attempt attempt = new Attempt();
        if(exam.getCourse()!=null)
        {
            if (exam.getCourse().getEnrollments().stream().noneMatch(enrollment -> enrollment.getUser().getId().equals(user.getId()))) {
                throw new UserInvalidException("User not enrolled in course");
            }
        } else if (!exam.getCreator().equals(user)&&!exam.isStatus()) {
            throw new UserInvalidException("User not enrolled in course");
        }

        List<Attempt> attempts = attemptService.findByUserExam(exam, user);
        for(Attempt a : attempts){
            if(a.isStatus()){
                return continueAttempt(a);
            }
        }
        attempt.setUser(user);
        attempt.setExam(exam);
        attempt.setStatus(true);
        attempt.setAttemptDate(new Timestamp(System.currentTimeMillis()));
        attempt.setAttemptHistories(attemptService.generateAttemptHistory(attempt, examService.generateQuestions(exam)));
        attemptService.saveAttempt(attempt);
        return continueAttempt(attempt);
    }

    private ModelAndView viewOldAttempt(Attempt attempt) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("attempt");
        modelAndView.addObject("attempt", attempt);
        List<AttemptHistory> attemptHistories = attemptService.getAttemptHistoryByAttemptId(attempt.getId());
        modelAndView.addObject("attemptHistories", attemptHistories);
        return modelAndView;
    }

    private ModelAndView continueAttempt(Attempt attempt) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("continue-attempt");
        if(attempt.getAttemptDate().getTime() + attempt.getExam().getLength()*60000 + 10000 < System.currentTimeMillis()){
            attempt.setStatus(false);
            attemptService.saveAttempt(attempt);
            return viewOldAttempt(attempt);
        }
        Timestamp endTime = new Timestamp(attempt.getAttemptDate().getTime() + attempt.getExam().getLength()*60000);
        modelAndView.addObject("endTimeMillis", endTime.getTime());
        modelAndView.addObject("attempt", attempt);
        return modelAndView;
    }

    @PostMapping("/exam={examId}/attempt={attemptId}/submit")
    public ModelAndView submitAttempt(Principal principal, @PathVariable Long attemptId, @PathVariable Long examId, @RequestParam Map<String, String> parameters) throws AttemptInvalidException, UserInvalidException {
        user = userService.findByUser(principal.getName());
        Attempt attempt = attemptService.findByAttemptId(attemptId);
        if(attempt == null){
            throw new AttemptInvalidException("Attempt not found");
        }
        if(!attempt.isStatus()){
            return viewOldAttempt(attempt);
        }
        if(!attempt.getUser().equals(user)){
            throw new UserInvalidException("Wrong user");
        }
        if(attempt.getAttemptDate().getTime() + attempt.getExam().getLength()*60000 + 10000 < System.currentTimeMillis()){
            attempt.setStatus(false);
            attemptService.saveAttempt(attempt);
            return viewOldAttempt(attempt);
        }
        attempt.setStatus(false);
        List<AttemptHistory> attemptHistories = attempt.getAttemptHistories();
        int mark = 0;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String questionId = entry.getKey(); // This is the question ID
            String answerIds = entry.getValue();
            System.out.println("Processing questionId: " + questionId + " with answerIds: " + answerIds);
            // You can now process the answer IDs for each question
            for (AttemptHistory attemptHistory : attemptHistories) {
                if (attemptHistory.getQuestion().getId().toString().equals(questionId)) {
                    Answer answer;
                    List<AttemptHistoryAnswer> attemptHistoryAnswers = new ArrayList<>();
                    answer = attemptService.convertStringToAnswer(answerIds);

                    AttemptHistoryAnswer attemptHistoryAnswer = new AttemptHistoryAnswer();
                    attemptHistoryAnswer.setAnswer(answer);
                    attemptHistoryAnswer.setAttemptHistory(attemptHistory);

                    attemptHistoryAnswers.add(attemptHistoryAnswer);

                  try{
                      attemptService.saveAttemptHistoryAnswer(attemptHistoryAnswer);
                  }catch (Exception e){
                      continue;}

                    attemptHistory.setAttemptHistoryAnswers(attemptHistoryAnswers);
                    if (attemptService.getCorrectAnswerOfQuestion(Long.parseLong(questionId)).contains(answer)) {
                        mark++;
                    }
                }
            }
        }

        attempt.setMark(mark);
        attempt.setAttemptHistories(attemptHistories);

        attemptService.saveAttempt(attempt);
        return viewOldAttempt(attempt);
    }



//    @RequestMapping("/exam={examId}/delete")
//    public ModelAndView deleteExam(@PathVariable Long examId) throws ExamInvalidException {
//        examService.deleteExam(examId);
//        return new ModelAndView("redirect:/home");
//    }
//
//    @GetMapping("/exam/{examId}/edit")
//    public ModelAndView editExamForm(@PathVariable Long examId) throws ExamInvalidException {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("edit_exam");
//        Exam exam = examService.findById(examId);
//        if (exam == null) {
//            throw new ExamInvalidException("Exam not found");
//        }
//        modelAndView.addObject("exam", exam);
//        return modelAndView;
//    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        ModelAndView mav = new ModelAndView("exam");
        mav.addObject("type", "error");
        mav.addObject("msg", "Missing parameter: " + ex.getParameterName());
        return mav;
    }

    @GetMapping("/exam-table")
    public ModelAndView viewExam(Principal principal, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {
        Pageable pageable = PageRequest.of(page, size);
        user = userService.findByUser(principal.getName());
        if(user == null){
            return new ModelAndView("redirect:/error");
        }
        Page<Exam> examList = examService.findAllExamWithStatus(user, false, pageable);
        List<Course> courseList = courseService.getAllCourse();

        ModelAndView mav = new ModelAndView();

        mav.addObject("examList", examList.getContent());
        mav.addObject("currentPage", page);
        mav.addObject("totalPages", examList.getTotalPages());
        mav.addObject("courseList", courseList);
        return mav;
    }

    @GetMapping("/exam-table/adding")
    public ModelAndView addExam(Principal principal) {
        ModelAndView mav = new ModelAndView();
        User user = userService.findByUser(principal.getName());
        if (user == null) {
            return new ModelAndView("redirect:/error");
        }
        else if(user.getRole().getName().contains("ADMIN")){
            List<Course> courseList = courseService.getAllCourse();
            mav.setViewName("exam-table-adding");
            mav.addObject("courseList", courseList);
        } else if (user.getRole().getName().contains("MANAGER")) {
            List<Course> courseList = courseService.getAllCourseByManager(user);
            mav.setViewName("exam-table-adding");
            mav.addObject("courseList", courseList);
        } else if (user.getRole().getName().contains("TEACHER")) {
            List<Course> courseList = courseService.getAllCourseByTeacher(user);
            mav.setViewName("exam-table-adding");
            mav.addObject("courseList", courseList);
        } else{
            List<Subject> subjectList = subjectService.getAllByStatus(true);
            mav.setViewName("exam-table-adding-subject");
            mav.addObject("subjectList", subjectList);
        }
        return mav;
    }

    @RequestMapping("/exam-table/addingPrivate")
    public String addExamPrivate(Principal principal, @RequestParam String name, @RequestParam String description, @RequestParam Long time, @RequestParam(required = true) Long subject, @RequestParam short question) throws ExamInvalidException {
        user = userService.findByUser(principal.getName());
        if (user == null) {
            throw new ExamInvalidException("User not found");
        }

        Exam exam = examService.addPrivateExam(name, description, user, subject, time, true, question);
        return "redirect:/exam="+exam.getId();
    }


    @RequestMapping("/exam-table/adding")
    public String addExam(Principal principal, @RequestParam String name, @RequestParam String description, @RequestParam Long time, @RequestParam Long course, @RequestParam short question) throws ExamInvalidException {
        user = userService.findByUser(principal.getName());
        if (user == null) {
            throw new ExamInvalidException("User not found");
        }
        if (courseService.findById(course) == null) {
            throw new ExamInvalidException("Course not found");
        }
        if(!user.getRole().getName().contains("MANAGER")&&!user.getRole().getName().contains("ADMIN")&&!user.getRole().getName().contains("TEACHER")&&!user.equals(courseService.findById(course).get().getTeacher())){
            throw new ExamInvalidException("User not creator");
        }
        Exam exam = examService.addExam(name, description, time, course, true, question);
        return "redirect:/exam="+exam.getId();
    }


    @RequestMapping("/exam-table/search")
    public ModelAndView search(HttpSession session, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "courseId", required = false) Long courseId, @RequestParam(value = "status", required = false) String status, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {
        ModelAndView mav = new ModelAndView();
        Pageable pageable = PageRequest.of(page, size);
        Page<Exam> examList = null;
        session.setAttribute("name", name);
        if (name != null && (courseId == null ) && (status == null || status.isEmpty())) {
            examList = examService.findAllByNameContaining(pageable, name);
        } else if (name != null && courseId != null  && (status == null || status.isEmpty())) {
            examList = examService.findAllByCourseIdAndNameContaining(courseId, name,pageable);
        } else if (name != null && (courseId == null ) && status != null && !status.isEmpty()) {
            boolean status2 = Boolean.parseBoolean(status);
            examList = examService.findAllByStatusAndNameContaining(status2, name,pageable);
        } else if (name != null && courseId != null  && status != null && !status.isEmpty()) {
            boolean status2 = Boolean.parseBoolean(status);
            examList = examService.findAllByCourseIdAndStatusAndNameContaining(courseId, status2,name,pageable);
        } else {
            examList = examService.findAll(pageable);
        }

        mav.setViewName("exam-table");
        mav.addObject("examList", examList.getContent());
        mav.addObject("currentPage", page);
        mav.addObject("totalPages", examList.getTotalPages());
        mav.addObject("courseList", courseService.getAllCourse());
        return mav;
    }


@GetMapping("/exam-list/{id}")
public ModelAndView edit(@PathVariable Long id, Principal principal) throws ExamInvalidException, UserInvalidException {
    ModelAndView mav = new ModelAndView();
    Exam exam = examService.findById(id);
    List<LessonExam> listLessonExam = lessonExamService.findAllByExamId(id);
    user = userService.findByUser(principal.getName());
    if (user == null) {
        throw new UserInvalidException("User not found");
    }
    if(lessonExamService.findAllByExamId(id) == null){
        throw new ExamInvalidException("Lesson not found");
    }
    if(user.getRole().getName().contains("MANAGER")&&user.getRole().getName().contains("ADMIN")&&user.getRole().getName().contains("TEACHER")&&!exam.getCreator().equals(user)){
        throw new UserInvalidException("User not creator");
    }
    mav.setViewName("exam-table-detail");
    mav.addObject("exam", exam);

    mav.addObject("subject", exam.getSubject());

    mav.addObject("lessonExam", listLessonExam);
    return mav;
}

    @PostMapping("/exam-table/edit/{id}")
    public String editExam(
            @RequestParam Map<String, String> allParams,
            Model model,
            HttpSession session,
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "length", required = false) Long length,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "status") boolean status) throws UserInvalidException, ExamInvalidException {

        Exam exam = examService.findById(id);
        if (user == null) {
            throw new UserInvalidException("User not found");
        }
        if(user.getRole().getName().contains("MANAGER")&&user.getRole().getName().contains("ADMIN")&&user.getRole().getName().contains("TEACHER")&&!exam.getCreator().equals(user)){
            throw new UserInvalidException("User not creator");
        }
        // Set session attributes
        session.setAttribute("name", name);
        session.setAttribute("description", description);

        // Update exam
        int sum = 0;
        // Process the number of questions for each lesson
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.startsWith("question")) {
                // Extract the index and value
                String index = key.substring(8); // Remove "question" prefix to get the index
                try {
                    int numberOfQuestions = Integer.parseInt(value);
                    System.out.println("Lesson Index: " + index + ", Number of Questions: " + numberOfQuestions);
                    sum+=numberOfQuestions;
                    lessonExamService.findById(Long.parseLong(index)).setNumberOfQuestions((short)numberOfQuestions);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format for question index " + index + ": " + value);
                }
            }
        }
        examService.updateUsingSaveMethod(id, name, length, (short)sum, description, status);

        return "redirect:/exam-list";
    }
    @RequestMapping("/exam-table/edit2/{id}/page={currentPage}")
    public String editStatusDirect(Principal principal, @PathVariable Long id, @PathVariable int currentPage) throws ExamInvalidException {
        Exam exam = examService.findById(id);
        if (exam == null) {
            throw new ExamInvalidException("Exam not found");
        }
        user=userService.findByUser(principal.getName());
        if (user == null) {
            throw new ExamInvalidException("User not found");
        }
        if(user.getRole().getName().contains("MANAGER")&&user.getRole().getName().contains("ADMIN")&&user.getRole().getName().contains("TEACHER")&&!exam.getCreator().equals(user)){
            throw new ExamInvalidException("User not creator");
        }
        if(exam.getCourse()!=null)
        {
            examService.updateByStatusId(id, !exam.isStatus());
        }
        return "redirect:/exam-table?page=" + currentPage;
    }

    @RequestMapping("/exam-list")
    public ModelAndView viewPrivateExam(Principal principal, HttpSession session, @RequestParam(value = "name", required = false, defaultValue = "") String name, @RequestParam(value = "courseId", required = false) Long courseId, @RequestParam(value = "subjectId", required = false) Long subjectId, @RequestParam(required = false, value = "status") Boolean status, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {
        Pageable pageable = PageRequest.of(page, size);
        user = userService.findByUser(principal.getName());
        if(user == null){
            return new ModelAndView("redirect:/error");
        }
        List<Subject> subjectList = subjectService.getAllByStatus(true);
        Page<Exam> examList = null;
        if(user.getRole().getName().contains("ADMIN"))
        {
            examList = examService.findAllExam(name, null, subjectId, courseId, status, pageable);
        }
        else if(user.getRole().getName().contains("MANAGER"))
        {
            for(SubjectManager subjectManager : user.getSubjectManager())
            {
                Subject subject = subjectManager.getSubject();
                examList = examService.findAllExam(name, null, subject.getId(), null, status, pageable);
                break;
            }
        }
        else{
            examList = examService.findAllExam(name, user.getId(), subjectId, courseId, status, pageable);
        }
        ModelAndView mav = new ModelAndView();
        session.setAttribute("name", name);
        mav.setViewName("exam-table");
        mav.addObject("examList", examList.getContent());
        mav.addObject("currentPage", page);
        mav.addObject("totalPages", examList.getTotalPages());
        mav.addObject("subjectList", subjectList);
        mav.addObject("courseList", courseService.getAllCourse());
        return mav;
    }
//nhac Giang autogenerate number of question
}
