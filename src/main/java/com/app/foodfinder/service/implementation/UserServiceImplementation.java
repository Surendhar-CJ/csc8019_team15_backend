package com.app.foodfinder.service.implementation;

import com.app.foodfinder.dto.UserDTO;
import com.app.foodfinder.entity.User;
import com.app.foodfinder.exception.InvalidPasswordException;
import com.app.foodfinder.exception.ResourceNotFoundException;
import com.app.foodfinder.exception.UserExistsException;
import com.app.foodfinder.dto.dtomapper.UserDTOMapper;
import com.app.foodfinder.repository.UserRepository;
import com.app.foodfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService
{
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, UserDTOMapper userDTOMapper, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    public UserDTO userRegister(User user)
    {
        User existingUser1 = userRepository.findByUsername(user.getUsername());

        if(existingUser1 != null)
        {
            throw new UserExistsException("Username already taken");
        }

        User existingUser2 = userRepository.findByEmail(user.getEmail());

        if(existingUser2 != null)
        {
            throw new UserExistsException("User with the email address '" + user.getEmail() + "' already exists");
        }

        String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(hashedPassword);

        userRepository.save(user);

        return userDTOMapper.apply(user);
    }

    @Override
    public UserDTO userLogin(String username, String password)
    {
        User user = userRepository.findByUsername(username);

        if (user == null || !username.matches(user.getUsername()) )
        {
            throw new UsernameNotFoundException("Invalid username");
        }
        else if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
        {
            throw new InvalidPasswordException("Invalid Password");
        }

        return userDTOMapper.apply(user);

    }

    @Override
    public UserDTO getUserById(Long id)
    {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent())
        {
            return userDTOMapper.apply(user.get());
        }
        else
        {
            throw new ResourceNotFoundException("User not found");
        }
   }


   @Override
    public void deleteUserById(Long id)
   {
       Optional<User> user = userRepository.findById(id);

       if(user.isPresent())
       {
           userRepository.deleteById(id);
       }
       else
       {
           throw new ResourceNotFoundException("User not found");
       }
   }

}
