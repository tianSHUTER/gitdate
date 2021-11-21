package io.kimmking.rpcfx.Exception;

import javax.swing.plaf.SeparatorUI;

public class rpcfxException extends Exception {
    public rpcfxException(){
        super();
    }

    @Override
    public void printStackTrace() {

        System.out.println("jason内容状态异常");
    }
}
