package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DiaryDaoGenerator
{
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "de.greenrobot.daoexample");

        addEntry(schema);

        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }

    private static void addEntry(Schema schema) {
        Entity note = schema.addEntity("Entry");
        note.addIdProperty().autoincrement();
        note.addStringProperty("title");
        note.addLongProperty("date");
        note.addStringProperty("content");
    }
}
