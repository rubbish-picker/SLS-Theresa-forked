package Theresa.action;

import Theresa.character.Theresa;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class TheresaAttackAction extends AbstractGameAction {
    public TheresaAttackAction(){
        this(false);
    }

    public TheresaAttackAction(boolean skill){
        this.skill = skill;
    }

    boolean skill;

    public void update() {
        Theresa.onAttack(skill);
        this.isDone = true;
    }
}
