package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Singleton implements Serializable {
    // Static variable reference of single_instance
    // of type Singleton
    private static Singleton single_instance = null;

    // Declaring a variable of type String
    public String message;

    static final long serialVersionUID = 1L;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private Singleton()
    {
        message = "Ainda sem mensagem atribuida.\n";
    }

    // Method
    // Static method to create instance of Singleton class
    public static Singleton Singleton()
    {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new Singleton();
        }
        return single_instance;
    }

    public void setMessage(String message) {
        if (message != null)
            this.message = message;
    }
}
