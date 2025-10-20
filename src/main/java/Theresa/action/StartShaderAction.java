package Theresa.action;

import Theresa.patch.BlackPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class StartShaderAction extends AbstractGameAction {
    public StartShaderAction() {}

    @Override
    public void update() {
        BlackPatch.beginShader();
        this.isDone = true;
    }
}
