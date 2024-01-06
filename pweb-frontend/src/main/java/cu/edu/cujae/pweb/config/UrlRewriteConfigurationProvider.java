package cu.edu.cujae.pweb.config;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

@RewriteConfiguration
public class UrlRewriteConfigurationProvider extends HttpConfigurationProvider {

    @Override
    public Configuration getConfiguration(ServletContext context) {
        return ConfigurationBuilder.begin()
                
        		.addRule(Join.path("/login").to("/pages/security/login.jsf"))
        		.addRule(Join.path("/home").to("/pages/home/home.jsf"))
        		.addRule(Join.path("/crud-issues").to("/pages/crud/issue/crud_issue.jsf"))
        		.addRule(Join.path("/crud-projects").to("/pages/crud/project/crud_project.jsf"))
        		.addRule(Join.path("/crud-rol").to("/pages/crud/rol/crud_rol.jsf"))
        		.addRule(Join.path("/crud-user").to("/pages/crud/user/crud_user.jsf"))
        		.addRule(Join.path("/crud-suggestion").to("/pages/crud/suggestion/crud_suggestion.jsf"))
        		.addRule(Join.path("/welcome").to("/pages/welcome/welcome.jsf"));

    
        //ej using params
        //.addRule(Join.path("/shop/{category}").to("/faces/viewCategory.jsf"));
    }

    @Override
    public int priority() {
        return 0;
    }

}
