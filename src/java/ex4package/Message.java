

package ex4package;

/**
 * object for storing msgs in session
 * @author Amichai Tribitch and Michael Ziskin
 */
public class Message {

    private String value;

    /**
     * constructor
     * @param value massage
     */
    public Message(String value) {
        this.value = value;
    }
    /**
     * get stored message
     * @return stored message
     */
    public String getValue() {
        return value;
    }
    
    /**
     * change stored message
     * @param value new message
     */
    public void setValue(String value) {
        this.value = value;
    }
}
