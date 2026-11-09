/*
 * IT Workshop Assignment: Library Management System
 *
 * A beginner-friendly, console-based program. Book information is kept in
 * memory while the program runs; it is not saved after the program exits.
 *
 * Sample interaction:
 *   1. Add Book -> enter ID 101, title "Kotlin Basics", author "A. Writer"
 *   2. Display Books -> the new book is listed as Available
 *   3. Issue Book -> enter 101 -> confirmation says it was issued
 *   4. Display Issued Books -> book 101 is listed
 *   5. Return Book -> enter 101 -> confirmation says it was returned
 *   6. Search Book -> enter 101 -> its details and Available status appear
 *   7. Exit
 *
 * To run: open this file in IntelliJ IDEA and run main(), or use the Kotlin
 * command-line compiler (kotlinc LibraryManagementSystem.kt -include-runtime
 * -d LibraryManagementSystem.jar) followed by java -jar LibraryManagementSystem.jar.
 */

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    var isIssued: Boolean = false
)

fun readPositiveInt(prompt: String): Int {
    while (true) {
        print(prompt)
        val value = readlnOrNull()?.trim()?.toIntOrNull()
        if (value != null && value > 0) return value
        println("Please enter a whole number greater than zero.")
    }
}

fun readNonBlank(prompt: String): String {
    while (true) {
        print(prompt)
        val value = readlnOrNull()?.trim().orEmpty()
        if (value.isNotEmpty()) return value
        println("This field cannot be blank. Please try again.")
    }
}

fun findBook(books: List<Book>, id: Int): Book? = books.find { it.id == id }

fun displayBook(book: Book) {
    val status = if (book.isIssued) "Issued" else "Available"
    println("ID: ${book.id} | Title: ${book.title} | Author: ${book.author} | Status: $status")
}

fun addBook(books: MutableList<Book>) {
    val id = readPositiveInt("Enter book ID: ")
    if (findBook(books, id) != null) {
        println("A book with ID $id already exists. Book was not added.")
        return
    }

    val title = readNonBlank("Enter book title: ")
    val author = readNonBlank("Enter author name: ")
    books.add(Book(id, title, author))
    println("Book added successfully.")
}

fun displayBooks(books: List<Book>) {
    if (books.isEmpty()) {
        println("There are no books in the library yet.")
        return
    }
    println("\nLibrary books:")
    books.forEach(::displayBook)
}

fun searchBook(books: List<Book>) {
    val id = readPositiveInt("Enter the book ID to search for: ")
    val book = findBook(books, id)
    if (book == null) println("No book found with ID $id.") else displayBook(book)
}

fun issueBook(books: List<Book>) {
    val id = readPositiveInt("Enter the book ID to issue: ")
    val book = findBook(books, id)
    when {
        book == null -> println("No book found with ID $id.")
        book.isIssued -> println("${book.title} is already issued.")
        else -> {
            book.isIssued = true
            println("${book.title} has been issued successfully.")
        }
    }
}

fun returnBook(books: List<Book>) {
    val id = readPositiveInt("Enter the book ID to return: ")
    val book = findBook(books, id)
    when {
        book == null -> println("No book found with ID $id.")
        !book.isIssued -> println("${book.title} is already available; it was not issued.")
        else -> {
            book.isIssued = false
            println("${book.title} has been returned successfully.")
        }
    }
}

fun displayIssuedBooks(books: List<Book>) {
    val issuedBooks = books.filter { it.isIssued }
    if (issuedBooks.isEmpty()) {
        println("There are no issued books.")
        return
    }
    println("\nIssued books:")
    issuedBooks.forEach(::displayBook)
}

fun printMenu() {
    println("""

--- Library Management System ---
1. Add Book
2. Display All Books
3. Search Book by ID
4. Issue Book
5. Return Book
6. Display Issued Books
7. Exit""")
}

fun main() {
    val books = mutableListOf<Book>()
    println("Welcome to the Library Management System!")

    while (true) {
        printMenu()
        val choice = readPositiveInt("Choose an option (1-7): ")

        // 'when' selects the task corresponding to the user's menu choice.
        when (choice) {
            1 -> addBook(books)
            2 -> displayBooks(books)
            3 -> searchBook(books)
            4 -> issueBook(books)
            5 -> returnBook(books)
            6 -> displayIssuedBooks(books)
            7 -> {
                println("Thank you for using the Library Management System. Goodbye!")
                return
            }
            else -> println("Invalid menu choice. Please choose a number from 1 to 7.")
        }
    }
}
