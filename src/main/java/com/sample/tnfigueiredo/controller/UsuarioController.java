/**
 * 
 */
package com.sample.tnfigueiredo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.tnfigueiredo.model.User;

/**
 * @author tnfigueiredo
 *
 */
@RestController
@RequestMapping("/users")
public class UsuarioController {
	
	@RequestMapping(method = RequestMethod.GET)
    public List<User> listarUsuarios(){
        return new ArrayList<User>();
    }

}
