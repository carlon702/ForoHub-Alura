package com.cjm.forum_hub.domain.users.dtoReq;

import com.cjm.forum_hub.domain.profile.Profile;

public record DataRegisterUserDB (String username, String email, String password, Profile profile){
}
