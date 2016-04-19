package my.vaadin.testing101;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import static com.vaadin.server.FontAwesome.*;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import static com.vaadin.ui.Notification.*;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import static org.jsoup.nodes.Document.OutputSettings.Syntax.html;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("my.vaadin.testing101.MyAppWidgetset")
public class MyUI extends UI {

    @Override
    @SuppressWarnings("empty-statement")
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        Label label = new Label("<b>Thank you</b> for clicking");
        label.setContentMode(ContentMode.HTML);
        layout.addComponent(label);
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
            Notification.show("Pushed");
        });
       // textfield.setComponentError(new UserError("Bad value"));
        button.setComponentError(new UserError("Bad click"));
        
       Notification note = new Notification("This is a warning","<br/>This is the <i>last</i> warning", TYPE_WARNING_MESSAGE);
        note.setHtmlContentAllowed(true);
        note.show(Page.getCurrent());
        
        Slider slider = new Slider("Drag the point");
        slider.setWidth(15, UNITS_EM);
        slider.setMin(0.0);
        slider.setMax(10.0);
        slider.setValue(5.0);
        slider.setImmediate(true);
        //slider.getCaption();
        
        
        TextField display = new TextField();
        layout.addComponent(display);
        display.setCaption("Score");
        
        slider.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
        String qw =  Double.toString(slider.getValue());
        display.setValue(qw);
            }
        });
        
        layout.addComponent(slider);
        
        
        
        
        //Adding Label
        layout.addComponent(new Label("Hello World"));
        
        layout.addComponents(name, button);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
