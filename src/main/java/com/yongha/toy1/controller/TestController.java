package com.yongha.toy1.controller;

import com.yongha.toy1.dto.TestDTO;
import com.yongha.toy1.entity.Test;
import com.yongha.toy1.repository.TestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    @Autowired
    TestRepository testRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<TestDTO> getTest(@RequestParam Long id) {
        logger.trace("trace log test");
        logger.debug("debug log test");
        logger.info("info log test");
        logger.warn("warn log test");
        logger.error("error log test");
        Test test = testRepository.getById(id);
        logger.info(test.getId() + test.getText());
        return new ResponseEntity<TestDTO>(new TestDTO(test), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value="/test", method = RequestMethod.POST)
    public ResponseEntity<TestDTO> postTest(@RequestParam String text) {
        Test test = new Test();
        test.setText(text);
        testRepository.save(test);
        logger.info(text);
        return new ResponseEntity<TestDTO>(new TestDTO(test), HttpStatus.ACCEPTED);
    }
}