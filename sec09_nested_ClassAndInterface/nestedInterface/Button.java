package sec09_nested_ClassAndInterface.nestedInterface;

public class Button {
    OnClickListener listener;

    void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    void touch(){
        listener.onClick();
    }

    interface OnClickListener {
        void onClick();
    }
}
