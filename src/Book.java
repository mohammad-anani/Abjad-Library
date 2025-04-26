import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Book implements IBook {
    private String ID = "";
    private String title = "";
    private String authorName = "";
    private int yearPublished = 0;
    private String genre = "";
    private int[] ratings = new int[5];
    private static final String fileName = "src/Files/books.txt";

    public Book() {
    }

    public Book(String ID, String title, String author, int yearPublished, String genre, int[] ratings) {
        this.ID = ID;
        this.title = title;
        this.authorName = author;
        this.yearPublished = yearPublished;
        this.genre = genre;
        this.ratings = ratings;
    }

    public Book(String title, String author, int yearPublished, String genre) {
        this.title = title;
        this.authorName = author;
        this.yearPublished = yearPublished;
        this.genre = genre;
    }

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public String getGenre() {
        return genre;
    }

    public void rateBook(int rating) {
        ratings[rating - 1]++;
    }


    public boolean validate() {
        return !( title.isEmpty() || authorName.isEmpty() || yearPublished < 0 || genre.isEmpty());
    }


    private boolean generateID() {
        if (!validate()) return false;
        Random rand = new Random();
        this.ID = title.substring(0, 3).toUpperCase() + "_" + authorName.substring(authorName.length() - 3) + "_" + (rand.nextInt(9000) + 1000);
        return true;
    }

    public boolean addBook() {
        if (generateID())
            return appendToFile();
        return false;
    }

    public boolean deleteBook() {
        ArrayList<Book> books = loadBookFromFile();
        ArrayList<Book> newBooks = new ArrayList<>();
        for (Book book : books) {
            if (!book.getID().equals(ID)) {
                newBooks.add(book);
            }
        }
            return writeToFile(newBooks);


    }

    public boolean updateBook() {
        ArrayList<Book> books = loadBookFromFile();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).equals(this)) {
                books.set(i, this);
                return writeToFile(books);
            }
        }
        return false;
    }

    public static ArrayList<Book> loadBookFromFile() {
        ArrayList<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int[] ratings = new int[5];
                String[] sRatings = parts[5].split("/");
                for (int i = 0; i <= 4; i++) {
                    ratings[i] = Integer.parseInt(sRatings[i]);
                }
                if (parts.length == 6) {
                    books.add(new Book(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4], ratings));
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return books;
    }

    private boolean writeToFile(ArrayList<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Book b : books) {
                bw.write(b.toCSVString());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean appendToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(this.toCSVString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Book findByID(String ID) {
        return loadBookFromFile().stream()
                .filter(b -> b.getID().equalsIgnoreCase(ID))
                .findFirst()
                .orElse(null);
    }

    public static ArrayList<Book> findByID(ArrayList<String> IDs) {
        ArrayList<Book> found = new ArrayList<>();
        for (Book b : loadBookFromFile()) {
            if (IDs.contains(b.getID())) {
                found.add(b);
            }
        }
        return found;
    }

    public boolean equals(Book book) {
        return this.ID.equals(book.getID());
    }

    public String toCSVString() {

        String[] strRatings = new String[ratings.length];
        for (int i = 0; i < ratings.length; i++) {
            strRatings[i] =""+ratings[i];
        }
        return ID + "," + title + "," + authorName + "," + yearPublished + "," + genre + ","+ String.join("/", strRatings);
    }

    @Override
    public String toString() {
        return "------------------------------\n" +
                "             Book             \n" +
                "------------------------------\n" +
                " ID            : " + ID + "\n" +
                " Title         : " + title + "\n" +
                " Author        : " + authorName + "\n" +
                " Year Published: " + yearPublished + "\n" +
                " Genre         : " + genre + "\n" +
                " Ratings       : 1: " + ratings[0] + "\n" +
                "                 2: " + ratings[1] + "\n" +
                "                 3: " + ratings[2] + "\n" +
                "                 4: " + ratings[3] + "\n" +
                "                 5: " + ratings[4] + "\n" +
                "------------------------------\n";
    }
}
