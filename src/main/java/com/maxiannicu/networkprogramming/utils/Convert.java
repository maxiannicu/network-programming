package com.maxiannicu.networkprogramming.utils;

import javax.mail.Address;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by nicu on 5/10/17.
 */
public class Convert {
    public static String addressesToString(Address[] addresses){
        return Arrays.stream(addresses).map(Address::toString).collect(Collectors.joining(","));
    }
}
