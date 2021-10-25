package ru.allexxsash.BookStore;

import ru.allexxsash.BookStore.Book;
import ru.allexxsash.BookStore.Customer;
import ru.allexxsash.BookStore.Employee;
import ru.allexxsash.BookStore.Order;

import java.util.ArrayList;

public class Main {

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        initDate();
        String booksInfo =
                String.format("Общее кол-во проданных книг %d на сумму %f", getCountOfSoldBooks(), getAllPricOfAllSoldBooks());
        System.out.println(booksInfo);
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
