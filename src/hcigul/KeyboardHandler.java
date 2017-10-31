package hcigul;

interface KeyboardHandlerListener{
    void onKeySelect(String key);
    void onKeyDelete(String key);
}

public class KeyboardHandler {
    private KeyboardHandlerListener listener;
    public int currentRow = 0;
    public int currentKey = 0;
    public String[][] keys = {
        {"A", "B", "C", "D", "E", "F", "G", "H", "Delete"},
        {"I", "J", "K", "L", "M", "N", "O", "P", "Delete All" },
        {"Q", "R", "S", "T", "U", "V", "W", "X",},
        {"Y", "Z", "Æ", "Ø", "Å", " ", ".", ","}
    };

    
    public void addListener(KeyboardHandlerListener listener) {
        this.listener = listener;
    }
    
    public void next() {
        currentKey = (currentKey + 1) % keys[currentRow].length;
    }
    
    public void down(){
        currentRow = (currentRow + 1) % keys.length;
    }
    
    public void select(){
        if (listener != null)
            listener.onKeySelect(keys[currentRow][currentKey]);
        
        if(keys[currentRow][currentKey] == "Delete" || keys[currentRow][currentKey] == "Delete All") 
            listener.onKeyDelete(keys[currentRow][currentKey]);
    }
}
