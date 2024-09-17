package com.example.workouttracker.core.utils;

import com.example.model.Exercise;
import com.example.model.ExerciseSet;
import com.example.model.TrainingDetails;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class ExcelGenerator {


    public static ByteArrayInputStream trainingsToExcel(List<TrainingDetails> trainings) throws IOException {
        String[] COLUMNs = {"Training Name", "Training Description", "Exercise Name", "Exercise Description", "Reps", "Weight"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Trainings");
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
            }

            int rowIdx = 1;
            for (TrainingDetails training : trainings) {
                for (Exercise exercise : training.getExercises()) {
                    for (ExerciseSet set : exercise.getSets()) {
                        Row row = sheet.createRow(rowIdx++);

                        row.createCell(0).setCellValue(training.getName());
                        row.createCell(1).setCellValue(training.getDescription());
                        row.createCell(2).setCellValue(exercise.getName());
                        row.createCell(3).setCellValue(exercise.getDescription());
                        row.createCell(4).setCellValue(set.getReps());
                        row.createCell(5).setCellValue(set.getWeight());
                    }
                }

                sheet.createRow(rowIdx++);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
