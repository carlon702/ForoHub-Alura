package com.cjm.forum_hub.service;

import com.cjm.forum_hub.domain.response.Response;
import com.cjm.forum_hub.domain.topics.Status;
import com.cjm.forum_hub.domain.topics.Topic;
import com.cjm.forum_hub.domain.topics.dtoReq.DataCreateResponse;
import com.cjm.forum_hub.domain.topics.dtoReq.DataCreateResponseToDB;
import com.cjm.forum_hub.domain.topics.dtoReq.DataUpdateResponse;
import com.cjm.forum_hub.domain.topics.dtoRes.DataResponseDeleteResponse;
import com.cjm.forum_hub.domain.topics.dtoRes.DataResponseInfo;
import com.cjm.forum_hub.domain.topics.dtoRes.DataResponseInfoTopicRes;
import com.cjm.forum_hub.domain.users.User;
import com.cjm.forum_hub.repository.ResponseRepository;
import com.cjm.forum_hub.repository.TopicRepository;
import com.cjm.forum_hub.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResponseService {
    @Autowired
    ResponseRepository responseRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserRepository userRepository;

    public DataResponseInfo getResponseById(Long responseId)
    {
        Optional<Response> responseGetter = responseRepository.findById(responseId);

        if(responseGetter.isEmpty())
        {
            throw new ValidationException("The response does not exist");
        }

        Response response = responseGetter.get();

        return new DataResponseInfo(response.getId(),
                response.getTopic().getId(),
                response.getMessage(),
                response.isSolution(),
                response.getCreationDate(),
                response.getAuthor().getId());
    }

    public DataResponseInfoTopicRes createResponse(DataCreateResponse dataCreateResponse)
    {
        //Find the objects with the IDs
        Optional<User> userGetter = userRepository.findById(dataCreateResponse.idAuthor());
        Optional<Topic> topicGetter = topicRepository.findById(dataCreateResponse.idTopic());

        if(userGetter.isEmpty())
        {
            throw new ValidationException("The user does not exist");
        }

        if(topicGetter.isEmpty())
        {
            throw new ValidationException("The topic does not exist");
        }

        DataCreateResponseToDB dataCreateResponseToDB = new DataCreateResponseToDB(
                dataCreateResponse.message(),
                topicGetter.get(),
                userGetter.get(),
                dataCreateResponse.solution(),
                LocalDateTime.now()

        );

        Response response = new Response(dataCreateResponseToDB);

        responseRepository.save(response);

        return fillData(topicGetter.get(), response);
    }

    public DataResponseInfoTopicRes updateResponse(Long idResponse, DataUpdateResponse dataUpdateResponse)
    {
        //Find the objects with the IDs
        Optional<User> userGetter = userRepository.findById(dataUpdateResponse.authorId());
        Optional<Topic> topicGetter = topicRepository.findById(dataUpdateResponse.topicId());

        if(userGetter.isEmpty())
        {
            throw new ValidationException("The user does not exist");
        }

        if(topicGetter.isEmpty())
        {
            throw new ValidationException("The topic does not exist");
        }

        Response response = responseRepository.getReferenceById(idResponse);



            User author = userRepository.getReferenceById(dataUpdateResponse.authorId());
            Topic topic = topicRepository.getReferenceById(dataUpdateResponse.topicId());

            response.setMessage(dataUpdateResponse.message());
            response.setAuthor(author);
            response.setTopic(topic);

        return fillData(topicGetter.get(), response);
    }

    public DataResponseInfoTopicRes updateStatus(Long idResponse){
        Response response = responseRepository.getReferenceById(idResponse);
        Topic topic = topicRepository.getReferenceById(response.getTopic().getId());
        response.setSolution(true);
        topic.setStatus(Status.CLOSED);
        return fillData(topic,response);
    }

    public DataResponseDeleteResponse deleteResponse(Long id)
    {
        try
        {
            DataResponseDeleteResponse dataResponseDeleteResponse = new DataResponseDeleteResponse();

            responseRepository.deleteById(id);

            return dataResponseDeleteResponse;
        }
        catch (Exception e)
        {
            throw new ValidationException(e.getMessage());
        }
    }

    public List<DataResponseInfoTopicRes> getResponsesByTopic(Long topicId)
    {
        Optional<Topic> topicGetter = topicRepository.findById(topicId);
        List<DataResponseInfoTopicRes> listResponses = new ArrayList<>();

        if(topicGetter.isEmpty())
        {
            throw new ValidationException("The topic does not exist");
        }

        Topic topic = topicGetter.get();

        List<Response> responsesList =  responseRepository.findByTopicId(topicId);

        for(Response res: responsesList)
        {
            DataResponseInfoTopicRes dataResponseInfoTopicRes = fillData(topic, res);

            listResponses.add(dataResponseInfoTopicRes);
        }

        return listResponses;
    }

    public DataResponseInfoTopicRes fillData(Topic topic, Response response)
    {

        return new DataResponseInfoTopicRes(
                topic.getId(),
                topic.getMessage(),
                response.getId(),
                response.getMessage(),
                response.isSolution(),
                response.getCreationDate(),
                response.getAuthor().getId()
        );
    }
}

