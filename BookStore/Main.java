package ru.allexxsash.BookStore;

import ru.allexxsash.BookStore.Book;
import ru.allexxsash.BookStore.Customer;
import ru.allexxsash.BookStore.Employee;
import ru.allexxsash.BookStore.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Main {

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        initDate();

        //вопрос №1 из списка задач программы
        String booksInfo =
                String.format("Общее кол-во проданных книг %d на сумму: %f", getCountOfSoldBooks(), getAllPricOfAllSoldBooks());
        System.out.println(booksInfo);

        //вопрос №2 из списка задач программы
        for (Employee employee : employees) {
            System.out.println(employee.getName() + " продал(а) " + getProfitByEmployee(employee.getId()).toString());
        }

        //вопрос №3 из списка задач программы
        ArrayList<BookAdditional> soldBooksCount = getCountOfSoldBooksByGenre();
        HashMap<BookGenre, Double> soldBookPrices = getPriceOfSoldBooksByGenre();

        String soldBooksStr = "По жанру: %s продано %d книг(и) общей стоимостью %f";

        for (BookAdditional bookAdditional : soldBooksCount){
            double price = soldBookPrices.get(bookAdditional.getGenre());
            System.out.println(String.format(soldBooksStr, bookAdditional.getGenre().name(),bookAdditional.getCount(),price));
        }

        //вопрос №4 из списка задач программы
        int age = 30;
        String analyzeGenreStr = "Покупатели младше %d лет выбирают жанр %s";
        System.out.println(String.format(analyzeGenreStr, 30, getMostPopularGenreLessThanAge(30)));

        //вопрос №5 из списка задач программы
        String analyzeGenreStr2 = "Покупатели старше %d лет выбирают жанр %s";
        System.out.println(String.format(analyzeGenreStr2, 30, getMostPopularGenreMoreThanAge(30)));

    }

    /**
     * получить наиболее популярный жанр для покупателей младше возраста #age
     * @param age требуемый возраст
     * @return популярный жанр
     */
    public static BookGenre getMostPopularGenreLessThanAge(int age){
        ArrayList<Long> customersIds = new ArrayList<>();

        for (Customer customer : customers){
            if(customer.getAge() < age){
                customersIds.add(customer.getId());
            }
        }
        return getMostPopularBookGenre(customersIds);
    }

    /**
     * получить наиболее популярный жанр для покупателей старше возраста #age
     * @param age требуемый возраст
     * @return популярный жанр
     */
    public static BookGenre getMostPopularGenreMoreThanAge(int age){
        ArrayList<Long> customersIds = new ArrayList<>();

        for (Customer customer : customers){
            if(customer.getAge() > age){
                customersIds.add(customer.getId());
            }
        }
        return getMostPopularBookGenre(customersIds);
    }

    private static BookGenre getMostPopularBookGenre(ArrayList<Long> customersIds) {
        int countArt = 0, countPr = 0, countPs = 0;

        for (Order order : orders){
            if(customersIds.contains(order.getCustomerId())){
                countArt += getCountOfSoldBooksByGenre(order, BookGenre.Art);
                countPr += getCountOfSoldBooksByGenre(order, BookGenre.Programming);
                countPs += getCountOfSoldBooksByGenre(order, BookGenre.Psychology);
            }
        }

        ArrayList<BookAdditional> result = new ArrayList<>();
        result.add(new BookAdditional(BookGenre.Art, countArt));
        result.add(new BookAdditional(BookGenre.Programming, countPr));
        result.add(new BookAdditional(BookGenre.Psychology, countPs));

        result.sort(new Comparator<BookAdditional>() {
            @Override
            public int compare(BookAdditional left, BookAdditional right) {
                return right.getCount() - left.getCount();
            }
        });

        return result.get(0).getGenre();
    }

    //получить кол-во проданных книг по жанрам
    public static ArrayList<BookAdditional> getCountOfSoldBooksByGenre(){
        ArrayList<BookAdditional> result = new ArrayList<>();
        int countArt = 0, countPr = 0, countPs = 0;
        for (Order order : orders){
            countArt += getCountOfSoldBooksByGenre(order, BookGenre.Art);
            countPr += getCountOfSoldBooksByGenre(order, BookGenre.Programming);
            countPs += getCountOfSoldBooksByGenre(order, BookGenre.Psychology);
        }
        result.add(new BookAdditional(BookGenre.Art, countArt));
        result.add(new BookAdditional(BookGenre.Programming, countPr));
        result.add(new BookAdditional(BookGenre.Psychology, countPs));

        return result;
    }

    /**
     * получить кол-во книг в одном заказе по определенному жанру
     * @param order заказ
     * @param genre жанр
     * @return кол-во книг
     */
    public static int getCountOfSoldBooksByGenre(Order order, BookGenre genre){
        int count = 0;

        for(long bookId :order.getBooks()){
            Book book = getBookById(bookId);
            if (book != null && book.getGenre() == genre)
            count++;
        }
        return count;
    }

    //получить стоимость проданных книг по жанрам
    public static HashMap<BookGenre, Double> getPriceOfSoldBooksByGenre(){
        HashMap<BookGenre, Double> result = new HashMap<>();
        double priceArt = 0, pricePr = 0, pricePs = 0;

        for (Order order : orders){
           priceArt += getPriceOfSoldBooksByGenre(order, BookGenre.Art);
           pricePr += getPriceOfSoldBooksByGenre(order, BookGenre.Programming);
           pricePs += getPriceOfSoldBooksByGenre(order, BookGenre.Psychology);
        }
        result.put(BookGenre.Art, priceArt);
        result.put(BookGenre.Programming, pricePr);
        result.put(BookGenre.Psychology, pricePs);

        return result;
    }

    /**
     * Получить общую сумму книг в одном заказе по определенному жанру
     * @param order заказ
     * @param genre жанр
     * @return общую стоимость
     */
    public static double getPriceOfSoldBooksByGenre(Order order, BookGenre genre){
        double price = 0;
        for (long bookId : order.getBooks()){
            Book book = getBookById(bookId);
            if(book != null && book.getGenre() == genre)
                price += book.getPrice();
        }
        return price;

    }

    /**
     * Получить общее количество и общую стоимость проданного товара для продавца
     * @param employeeId уникальный номер продавца
     * @return количество и общую стоимость указанного продавца
     */
    public static Profit getProfitByEmployee(long employeeId){
        int count = 0; double price = 0;
        for (Order order : orders){
            if(order.getEmployeeId() == employeeId){
               price += getPriceOfSoldBooksInOrder(order);
               count += order.getBooks().length;
            }
        }
        return new Profit(count, price);
    }

    //получить общую сумму заказов
    public static double getAllPricOfAllSoldBooks(){
        double price = 0;
        for (Order order : orders){
            price += getPriceOfSoldBooksInOrder(order);
        }
        return price;
    }

    /**
     * получить общую стоимость одного заказа
     * @param order заказ, по которому считается стоимость
     * @return общая стоимость для всех проданных книг в заказе
     */
    public static double getPriceOfSoldBooksInOrder(Order order){
        double price = 0;
        for (long bookId : order.getBooks()){
          Book book = getBookById(bookId);
            if(book != null)
          price += book.getPrice();
        }
        return price;
    }

    //получить общее кол-во проданных книг
    public static int getCountOfSoldBooks(){
       int count = 0;
       for (Order order : orders){
           count += order.getBooks().length;
       }
       return count;
    }

    /**
     * Поиск книги в списке книг по ее уникальному номера
     * @param id уникальный номер книги
     * @return найденная книга
     */
    public static Book getBookById(long id){
        Book current = null;

        for (Book book : books){
          if(book.getId() == id) {
              current = book;
              break;
          }
        }
        return current;
    }



    public static void initDate(){
      //продавцы
        employees.add(new Employee(1,"Иванов Иван",32));
        employees.add(new Employee(2,"Петров Петр",30));
        employees.add(new Employee(3,"Васильева Алиса",26));

        //покупатели
        customers.add(new Customer(1,"Сидоров Алексей",25));
        customers.add(new Customer(2,"Романов Дмитрий",32));
        customers.add(new Customer(3,"Симонов Кирилл",19));
        customers.add(new Customer(4,"Кириенко Данил",45));
        customers.add(new Customer(5,"Воротынцева Элина",23));

        //книги
        books.add(new Book(1,"Война и мир","Толстой Лев",1600,BookGenre.Art));
        books.add(new Book(2,"Преступление и наказание","Достоевский Федор",600,BookGenre.Art));
        books.add(new Book(3,"Мертвые души","Гоголь Николай",750,BookGenre.Art));
        books.add(new Book(4,"Руслан и Людмила","Пушкин Александр",500,BookGenre.Art));

        books.add(new Book(5,"Введение в психоанализ","Фрейд Зигмунд",1050,BookGenre.Psychology));
        books.add(new Book(6,"Психология влияния. Убеждай. Воздействуй. Защищайся","Чалдини Роберт",550,BookGenre.Psychology));
        books.add(new Book(7,"Как перестать беспокоиться и начать жить","Карнеги Дейл",1000,BookGenre.Psychology));

        books.add(new Book(8,"Gang Of Four","Гамма Эрих",900,BookGenre.Programming));
        books.add(new Book(9,"Совершенный код","Макконнел Стив",1200,BookGenre.Programming));
        books.add(new Book(10,"Рефакторинг. Улучшение существующего кода","Фаулер Мартин",850,BookGenre.Programming));
        books.add(new Book(11,"Алгоритмы. Построение и анализ","Корман Томас",450,BookGenre.Programming));

        orders.add(new Order(1,1,1,new long[]{8,9,10,11}));
        orders.add(new Order(2,1,2,new long[]{1}));

        orders.add(new Order(3,2,3,new long[]{5,6,7}));
        orders.add(new Order(4,2,4,new long[]{1,2,3,4}));

        orders.add(new Order(5,3,5,new long[]{2,5,9}));




    }
}
