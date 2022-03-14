package com.example.viernes.Controller;

import com.example.viernes.Model.User;
import com.example.viernes.Model.UserData;
import com.example.viernes.Model.UtilsObject.Mensaje;
import com.example.viernes.Model.UtilsObject.UserDataView;
import com.example.viernes.Repository.TelefonoRepository;
import com.example.viernes.Repository.UserDataRepository;
import com.example.viernes.Repository.UsuarioRepository;
import com.example.viernes.Utils.CreateToken;
import com.example.viernes.Utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/User")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TelefonoRepository telefonoRepository;
    @Autowired
    UserDataRepository userDataRepository;

    Validator validator = new Validator();

    @GetMapping
    public List<User> allUserView(){
         List<User> users = usuarioRepository.findAll();
        for (int i = 0; i < 10; i++) {
            System.out.println(new CreateToken().genenrateTokenUUID());
        }
        return users;
    }

    @PostMapping
    public Mensaje addUser(@RequestBody User user){

        Mensaje m = new Mensaje();
        CreateToken createToken = new CreateToken();
        Optional<User> o = Optional.ofNullable((usuarioRepository.findByEmail(user.getEmail())));


        if(!o.isPresent() && validator.validatorPassword(user.getPassword()) && validator.validatorEmail(user.getEmail()))
        {
            UserData userD = new UserData();

            userD.setCreated(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
            userD.setModified(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
            userD.setLastlogin(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
            userD.setIsactive(true);
            userD.setToken(createToken.genenrateTokenUUID());

            User u = usuarioRepository.save(user);
            userD.setUserId(u.getId());
            userDataRepository.save(userD);
            UserDataView dataView = new UserDataView();
            dataView.setCreated(userD.getCreated());
            dataView.setModified(userD.getModified());
            dataView.setLastlogin(userD.getLastlogin());
            dataView.setIsactive(userD.isIsactive());
            dataView.setToken(userD.getToken());
            String idString = u.getId().toString();
            dataView.setUser( UUID.nameUUIDFromBytes( String.valueOf( idString).getBytes() ).toString());
            m.setObject(dataView);
            return m;
        }
        else{
            if(o.isPresent()){
                m.setObject( new String("El email ya existe! "));
            }
            else if(!validator.validatorEmail(user.getEmail())){
                m.setObject( new String("El Email de cumplir el formato: aaaaaaa@dominio.cl"));
            }
            else if(validator.validatorPassword(user.getPassword())){
                m.setObject( new String("La contraseña de cumplir el formato: 1 Mayuscula, n minusculas, 2 numeros"));
            }
        }

        return m;
    }
}
