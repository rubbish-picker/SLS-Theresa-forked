package Theresa.action;

import Theresa.power.debuff.DyingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TimeToDieAction extends AbstractGameAction {
    public TimeToDieAction(int amt){
        this.amount = amt;
    }

    @Override
    public void update() {
        AbstractMonster max = null;
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            if(!m.isDeadOrEscaped()){
                if(max==null || m.currentHealth>max.currentHealth){
                    max = m;
                }
            }
        }
        if(max!=null){
            addToTop(new ApplyPowerAction(max,AbstractDungeon.player,new DyingPower(max,amount),amount));
        }
        this.isDone = true;
    }
}
