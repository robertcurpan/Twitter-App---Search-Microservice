package com.twitterapp.searchmicroservice.extrastructures;

public enum UserTypeEnum
{
    LIVE("LIVE"),
    ACTIVE("ACTIVE"),
    PASSIVE("PASSIVE");

    private final String userType;

    private UserTypeEnum(final String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return this.userType;
    }
}
