package src.main.java;

import java.util.*;
import java.net.*;
import java.io.*;

import com.google.gson.*;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(5000);
        System.out.println(server.aliasDatabase);
    }
}