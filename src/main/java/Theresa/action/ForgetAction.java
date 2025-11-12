package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ForgetAction extends AbstractGameAction {
    public ForgetAction(int amount) {
        this.amount = amount;
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
        if (max != null) {
            int damage = (int) ((float)max.currentHealth * amount / 100F);
            if(damage<amount)
                damage = amount;
            addToTop(new DamageAction(max,new DamageInfo(AbstractDungeon.player,damage, DamageInfo.DamageType.HP_LOSS),AttackEffect.FIRE,true));
        }
        this.isDone = true;
    }
}
