/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.utils.JsfUtils;
import java.util.Locale;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author nandita
 */
@Component
@ManagedBean
@ApplicationScoped
public class LanguageBean {

    private Locale locale = JsfUtils.getCurrentLocale();;
    private boolean english;

    public LanguageBean(){
        
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        FacesContext.getCurrentInstance().getApplication().setDefaultLocale(locale);

    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isEnglish() {
        return english;
    }

    public void setEnglish(boolean english) {
        this.english = english;

    }

    public void checkboxListener() {
        if(english){
            locale = new Locale("en");
        }
        else {
            locale = new Locale("es");
        }
//        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        FacesContext.getCurrentInstance().getApplication().setDefaultLocale(locale);
        System.out.println("Checkbox value changed to: " + english);
    }
}
