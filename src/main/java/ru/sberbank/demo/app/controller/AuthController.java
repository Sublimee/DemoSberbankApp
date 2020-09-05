//package ru.sberbank.demo.app.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/login")
//public class AuthController {
//
//    @GetMapping
//    ResponseEntity<Ent> getEnt() {
//        return ResponseEntity.status(HttpStatus.OK).body(new Ent());
//    }
//
//
//    class Ent {
//        private Object result;
//
//        public Ent() {
//            this.result = 5;
//        }
//
//        public Object getResult() {
//            return result;
//        }
//
//        public void setResult(Object result) {
//            this.result = result;
//        }
//    }
//}
