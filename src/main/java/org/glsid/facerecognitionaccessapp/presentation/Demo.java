package org.glsid.facerecognitionaccessapp.presentation;

import org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite.SQLiteConnection;

public class Demo {
    public static void main(String[] args) {
        SQLiteConnection connection = new SQLiteConnection();
        if (connection.getConnection() != null) {
            System.out.println("Connection established successfully.");
            connection.closeConnection();
        } else {
            System.out.println("Failed to establish connection.");
        }
    }
}
