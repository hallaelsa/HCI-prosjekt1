package hcigul;
import javafx.animation.AnimationTimer;

interface PressHandlerListener{
    void onSingleClick();
    void onDoubleClick();
    void onLongPress();
}

public class PressHandler {
    private PressHandlerListener listener;
    private boolean isPressed = false;
    private long pressTime = 0;
    private long releaseTime = 0;
    private boolean ignoreRelease = false;
    private int singleClickThreshold = 100;
    private int longPressThreshold = 400;
    
    public void addListener(PressHandlerListener listener) {
        this.listener = listener;
    }
    
    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (!isPressed && releaseTime == 0)
                return;
            
            long releaseDifference = (now - releaseTime) / 1000000;
            
            if (!ignoreRelease && !isPressed && releaseTime > 0 && releaseDifference > singleClickThreshold)
            {
                releaseTime = 0;
                
                if (listener != null)
                    listener.onSingleClick();
            }
            
            long pressDifference = (now - pressTime) / 1000000;
            
            if (isPressed && pressTime != 0 && pressDifference > longPressThreshold)
            {
                ignoreRelease = true;
                pressTime = now;
                
                if (listener != null)
                    listener.onLongPress();
            }
        }
    };
    
    public PressHandler(){
        timer.start();
    }
    
    public void Pressed(){
        if(isPressed)
            return;

        isPressed = true;
        ignoreRelease = false;
        pressTime = System.nanoTime();

        if (releaseTime > 0)
        {
            ignoreRelease = true;
            
            if (listener != null)
                    listener.onDoubleClick();
        }
    }
    
    public void Released() {
        isPressed = false;
        releaseTime = ignoreRelease ? 0 : System.nanoTime();
    }
}
