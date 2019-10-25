package gui_artist;

public interface MyFrame {
    /**
     * Call this method if you draw your frame first time
     * @throws InterruptedException
     **/
    void drawFrame() throws InterruptedException;

    /**
     * Call this method to redraw your frame
     * @throws InterruptedException
     **/
    void redrawFrame() throws InterruptedException;

    /**
     * This method add all components to your frame
     **/
    void drawElements();

    /**
     * Call this method to tune your frame if the {@link #drawFrame()} has been called
     **/
    void setDefaultFrameSettings();

    /**
     * Call this method to tune your frame if the {@link #redrawFrame()} has been called
     **/
    void setRepeatFrameSettings();

    /**
     * This method allows to show a dialog, that helps user to read the information
     **/
    void showInfoDialog();

    /**
     * This method allows to show a dialog, that contains an information about connection error
     * @throws InterruptedException
     **/
    default void showErrorDialog() throws InterruptedException{}
}
