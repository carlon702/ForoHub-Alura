package com.cjm.forum_hub.service;

import com.cjm.forum_hub.domain.course.Course;
import com.cjm.forum_hub.domain.response.Response;
import com.cjm.forum_hub.domain.topics.Status;
import com.cjm.forum_hub.domain.topics.Topic;
import com.cjm.forum_hub.domain.topics.dtoReq.DataCreateTopic;
import com.cjm.forum_hub.domain.topics.dtoReq.DataCreateTopicToDB;
import com.cjm.forum_hub.domain.topics.dtoReq.DataTopicSearchCourseAndDate;
import com.cjm.forum_hub.domain.topics.dtoReq.DataUpdateTopic;
import com.cjm.forum_hub.domain.topics.dtoRes.DataResponseDeleteTopic;
import com.cjm.forum_hub.domain.topics.dtoRes.DataResponseGetTopic;
import com.cjm.forum_hub.domain.topics.dtoRes.DataResponseTopic;
import com.cjm.forum_hub.domain.users.User;
import com.cjm.forum_hub.domain.users.dtoRes.DataUserRes;
import com.cjm.forum_hub.repository.CourseRepository;
import com.cjm.forum_hub.repository.ResponseRepository;
import com.cjm.forum_hub.repository.TopicRepository;
import com.cjm.forum_hub.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    ResponseRepository responseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;



    public List<DataResponseGetTopic> getAllDataTopic()
    {
        List<Topic> listTopics = topicRepository.findAll();
        List<DataResponseGetTopic> topics = new ArrayList<>();

        for(Topic t :listTopics)
        {
            DataResponseGetTopic dataTopic = createTopicDTO(t);

            topics.add(dataTopic);
        }

        return topics;
    }

    public DataResponseGetTopic createTopic(DataCreateTopic dataCreateTopic)
    {
        Optional<User> userGetter = userRepository.findById(dataCreateTopic.userId());
        Optional<Course> courseGetter = courseRepository.findById(dataCreateTopic.courseId());


        if(userGetter.isEmpty())
        {
            throw new ValidationException("The user doesn't exist");
        }

        if(courseGetter.isEmpty())
        {
            throw new ValidationException("The course doesn't");
        }


        DataCreateTopicToDB dataCreateTopicToDB = new DataCreateTopicToDB(
                dataCreateTopic.title(),
                dataCreateTopic.message(),
                userGetter.get(),
                courseGetter.get(),
                Status.OPEN
        );



        Topic newTopic = new Topic(dataCreateTopicToDB);

        topicRepository.save(newTopic);

        return createTopicDTO(newTopic);
    }

    public DataResponseGetTopic getTopicById(Long id)
    {
        Optional<Topic> optionalTopic = topicRepository.findById(id);

        if(optionalTopic.isEmpty())
        {
            throw new ValidationException("The topic doesn't exist");
        }

        Topic topicData = optionalTopic.get();

        return createTopicDTO(topicData);
    }

    public DataResponseGetTopic updateTopic(Long id, DataUpdateTopic dataUpdateTopic)
    {
        Optional<Topic> topicToUpdate= topicRepository.findById(id);

        if(topicToUpdate.isEmpty())
        {
            throw new ValidationException("The topic doesn't exist");
        }

        Topic topic = topicToUpdate.get();

        Optional<User> userGetter = userRepository.findById(dataUpdateTopic.user());
        Optional<Course> courseGetter = courseRepository.findById(dataUpdateTopic.course());

        if(userGetter.isEmpty())
        {
            throw new ValidationException("The user does not exist");
        }

        if(courseGetter.isEmpty())
        {
            throw new ValidationException("The course does not exist");
        }


        topic.setMessage(dataUpdateTopic.message());
        topic.setTitle(dataUpdateTopic.title());
        topic.setAuthor(userGetter.get());
        topic.setCourse(courseGetter.get());

        return createTopicDTO(topic);
    }

    public DataResponseDeleteTopic DeleteTopic(Long id)
    {
        DataResponseDeleteTopic dataResponseDeleteTopic = new DataResponseDeleteTopic("Topic deleted");

        topicRepository.deleteById(id);

        return dataResponseDeleteTopic;
    }


    public DataResponseGetTopic createTopicDTO(Topic topicData)
    {
        List<Response> listResponses = responseRepository.findByTopic(topicData);

        List<DataResponseTopic> listDtoResponseTopic = listResponses.stream()
                .map(r -> new DataResponseTopic(r.getCreationDate(),
                        r.getMessage(),
                        r.isSolution(),
                        new DataUserRes(r.getAuthor().getUsername(), r.getAuthor().getEmail())))
                .toList();

        return new DataResponseGetTopic(
                topicData.getId(),
                topicData.getTitle(),
                topicData.getMessage(),
                topicData.getCreationDate(),
                topicData.getStatus(),
                new DataUserRes(topicData.getAuthor().getUsername(), topicData.getAuthor().getEmail()),
                topicData.getCourse().getCourseName(),
                listDtoResponseTopic
        );
    }
}
