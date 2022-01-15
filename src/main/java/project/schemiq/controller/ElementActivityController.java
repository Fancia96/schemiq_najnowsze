package project.schemiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.schemiq.model.ElementActivityModel;
import project.schemiq.model.UserModel;
import project.schemiq.repository.ElementActivityRepository;
import project.schemiq.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ElementActivityController {
    @Autowired
    private ElementActivityRepository elementActivityRepository;
    @Autowired
    private UserRepository userRepository;

    public void ElementController(ElementActivityRepository elementActivityRepository, UserRepository userRepository){
        this.elementActivityRepository = elementActivityRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/activity/{userId}")
    public ResponseEntity<List<ElementActivityModel>> getActivity(@PathVariable Long userId) {
        Optional<UserModel> user = userRepository.findUserById(userId);

        if (user.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        Optional<List<ElementActivityModel>> list = elementActivityRepository.findByUserID(user.get());
        if (list.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(list.get());
    }
}
