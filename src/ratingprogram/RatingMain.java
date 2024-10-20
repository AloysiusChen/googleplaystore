package ratingprogram;

import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

//Remove Life Made WI-Fi Touchscreen Photo Frame

public class RatingMain{
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String csvFile = args[0];

        Reader reader = new FileReader(csvFile);
        BufferedReader bufferedReader = new BufferedReader(reader);
        
        String line;
        String header = bufferedReader.readLine();

        class App {
            public String name;
            public String category;
            public float rating;
        

        public App(String name, String category, float rating) {
            this.name = name;          
            this.category = category;
            this.rating = rating;
        }

        @Override
        public String toString() {
            return "App{name=" + name + ", category=" + category + ", rating=" + rating + "}";
        }
        }

        class CategoryStats {
            App highestRatedApp;
            App lowestRatedApp;
            float averageRating;

            public CategoryStats(App highestRatedApp, App lowestRatedApp, float averageRating) {
                this.highestRatedApp = highestRatedApp;
                this.lowestRatedApp = lowestRatedApp;
                this.averageRating = averageRating;
            }

            @Override
            public String toString() {
                return "CategoryStats{" +
                        "highestRatedApp=" + highestRatedApp +
                        ", lowestRatedApp=" + lowestRatedApp +
                        ", averageRating=" + averageRating +
                        '}';
            }        
        }        

        List<App> appList = new ArrayList<>();
        HashMap<String, ArrayList<App>> categoryMap = new HashMap<>();

        while((line = bufferedReader.readLine()) != null){
            String[] cell = line.split(",");
            float parsedFloat = Float.parseFloat(cell[2]);
            App app = new App(cell[0], cell[1], parsedFloat);
            appList.add(app);
        }
        reader.close();

        for(int index=0 ; index < appList.size() ; index++){
            String category = appList.get(index).category;

            if(!categoryMap.containsKey(category)){
                categoryMap.put(category, new ArrayList<App>());
            }

            ArrayList<App> appListForCategory = categoryMap.get(category);
            appListForCategory.add(appList.get(index));
        }

        HashMap<String, CategoryStats> categoryStatsMap = new HashMap<>();

        for(String category : categoryMap.keySet()){
            ArrayList<App> apps = categoryMap.get(category);
            App highestRatedApp = null;
            App lowestRatedApp = null;
            float totalRating = 0;

            int count = 0;

            for(App app : apps){
                if(!Float.isNaN(app.rating)){
                totalRating += app.rating;
                count++;
                }

                if(highestRatedApp == null || app.rating > highestRatedApp.rating){
                    highestRatedApp = app;
                }

                if(lowestRatedApp == null || app.rating < lowestRatedApp.rating){
                    lowestRatedApp = app;
                }
            }

            float averageRating = totalRating / count;
            categoryStatsMap.put(category, new CategoryStats(highestRatedApp, lowestRatedApp, averageRating));
        }

        System.out.println("See here:\n");
        System.out.println(categoryStatsMap);

   }
}