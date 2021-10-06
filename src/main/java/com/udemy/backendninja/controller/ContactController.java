package com.udemy.backendninja.controller;


import com.udemy.backendninja.constant.ViewConstant;
import com.udemy.backendninja.model.ContactModel;
import com.udemy.backendninja.service.ContactService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private static final Log LOG= LogFactory.getLog(ContactController.class);

    @Autowired
    @Qualifier("contactServiceImpl")
    private ContactService contactService;

    //Permite escribir expresiones de Spring del tipo hasRole y así si el rol existe en la bd permite mostrar
    // el método o clase dependiendo de lo que se desea -- si escribes un rol no existente de muestra la platilla 403.html
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/contactform")
    private String redirectContactForm(@RequestParam(name = "id", required = false)int id,
                                       Model model){
        ContactModel contact = new ContactModel();
        if(id != 0){
            contact = contactService.findContactByIdModel(id);
        }
        model.addAttribute("contactModel",contact);
        return ViewConstant.CONTACT_FORM;
    }

    @GetMapping("/cancel")
    private String cancel(){

        return "redirect:/contacts/showcontacts";
    }

    @PostMapping("/addcontact")
    private String addContact(@ModelAttribute(name = "contactModel") ContactModel contactModel,
                              Model model){
        LOG.info("METHOD: addcontact() -- PARAMS: "+ contactModel.toString());
        if(null != contactService.addContact(contactModel) ){
            model.addAttribute("result",1);
        }else{
            model.addAttribute("result",0);
        }
        return "redirect:/contacts/showcontacts";
    }
    @GetMapping("/showcontacts")
    public ModelAndView showContacts() {
        ModelAndView mav = new ModelAndView(ViewConstant.CONTACTS);

        //Obtiene el usuario que se encuentra loggeado en este moment9
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Se obtiene el username
        mav.addObject("username", user.getUsername());


        mav.addObject("contacts",contactService.listAllContacts());
        return mav;
    }
    @GetMapping("/removecontact")
    public ModelAndView removeContact(@RequestParam( name = "id", required = true) int id){
        contactService.removeContact(id);
        return showContacts();
    }


}
