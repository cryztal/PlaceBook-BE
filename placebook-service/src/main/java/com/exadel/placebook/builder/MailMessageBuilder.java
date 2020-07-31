package com.exadel.placebook.builder;

import com.exadel.placebook.dao.OfficeDao;
import com.exadel.placebook.dao.PlaceDao;
import com.exadel.placebook.dao.UserDao;
import com.exadel.placebook.model.dto.BookingDto;
import com.exadel.placebook.model.dto.BookingRequest;
import com.exadel.placebook.model.dto.MailMessageDto;
import com.exadel.placebook.model.entity.*;
import com.exadel.placebook.model.exception.SendMessageException;
import com.exadel.placebook.service.UserService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class MailMessageBuilder {

    @Autowired
    private Configuration freemarkerConfig;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OfficeDao officeDao;

    @Autowired
    private UserService userService;

    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MailMessageDto convert(BookingDto bookingDto) {
        try {
            freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
            Template temp = freemarkerConfig.getTemplate("email-template.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("name", bookingDto.getUserName());
            model.put("text", "Your booking is CANCELLED!");
            model.put("country", bookingDto.getAddress().getCountry());
            model.put("city", bookingDto.getAddress().getCity());
            model.put("office", bookingDto.getAddress().getAddress());
            model.put("placeNumber", bookingDto.getPlaceNumber());
            model.put("timeStart", bookingDto.getTimeStart().format(formatter));
            model.put("timeEnd", bookingDto.getTimeEnd().format(formatter));

            String text = FreeMarkerTemplateUtils.processTemplateIntoString(temp, model);
            MailMessageDto message = new MailMessageDto(text, bookingDto.getEmail());
            return message;
        } catch (IOException e) {
            throw new SendMessageException("Send email exception! IOException");
        } catch (TemplateException e) {
            throw new SendMessageException("Send email exception! TemplateException");

        }
    }

    public MailMessageDto convert(BookingRequest bookingRequest, Long userId) {
        try {
            freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
            Template temp = freemarkerConfig.getTemplate("email-template.ftl");
            Address address = officeDao.findOfficeById(bookingRequest.getOfficeId()).getAddress();

            Map<String, Object> model = new HashMap<>();
            model.put("name", userDao.find(userService.getUserStatus().getId()).getName());
            model.put("text", "You have just booked your place!");
            model.put("country", address.getCountry());
            model.put("city", address.getCity());
            model.put("office", address.getAddress());
            model.put("placeNumber", bookingRequest.getPlaceNumber());
            model.put("timeStart", bookingRequest.getTimeStart().format(formatter));
            model.put("timeEnd", bookingRequest.getTimeEnd().format(formatter));
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(temp, model);
            MailMessageDto message = new MailMessageDto(text, userDao.find(userId).getEmail());
            return message;
        } catch (IOException e) {
            throw new SendMessageException("Send email exception! IOException");
        } catch (TemplateException e) {
            throw new SendMessageException("Send email exception! TemplateException");

        }
    }

    public MailMessageDto convert(Subscribe subscribe) {
        try {
            freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
            Template temp = freemarkerConfig.getTemplate("notification-template.ftl");

            Place place = subscribe.getPlace();
            Address address = place.getFloor().getOffice().getAddress();
            User user = subscribe.getUser();

            Map<String, Object> model = new HashMap<>();
            model.put("name", user.getName());
            model.put("text", "We are glad to tell you, that the place you subscribed to is free! have time to book!");
            model.put("country", address.getCountry());
            model.put("city", address.getCity());
            model.put("office", address.getAddress());
            model.put("placeNumber", place.getPlaceNumber());

            String text = FreeMarkerTemplateUtils.processTemplateIntoString(temp, model);
            MailMessageDto message = new MailMessageDto(text, user.getEmail());

            return message;
        } catch (IOException e) {
            throw new SendMessageException("Send email exception! IOException");
        } catch (TemplateException e) {
            throw new SendMessageException("Send email exception! TemplateException");

        }
    }
}
