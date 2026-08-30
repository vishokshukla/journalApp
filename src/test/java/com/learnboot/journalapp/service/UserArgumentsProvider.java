package com.learnboot.journalapp.service;

import com.learnboot.journalapp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(Arguments.of(User.builder().username("Rama").password("123").journalEntries(new ArrayList<>()).roles(Arrays.asList("USER","ADMIN")).build()),
                Arguments.of(User.builder().username("Shiva").password("123").journalEntries(new ArrayList<>()).roles(Arrays.asList("USER")).build()));
    }
}
