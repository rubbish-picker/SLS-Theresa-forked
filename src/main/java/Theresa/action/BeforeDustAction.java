package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BeforeDustAction extends AbstractGameAction {
    public BeforeDustAction(int shouldAmount,AbstractCreature target, int damage, DamageInfo.DamageType type, AttackEffect effect){
        this.shouldAmount = shouldAmount;
        this.target = target;
        this.amount = damage;
        this.damageType = type;
        this.attackEffect = effect;
        this.startDuration = duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DISCARD;
    }

    public int shouldAmount = 0;

    @Override
    public void update() {
        if(DrawCardAction.drawnCards.size()<shouldAmount){
            int sub = shouldAmount-DrawCardAction.drawnCards.size();
            for(int i=0;i<sub;i++){
                addToTop(new DamageAction(target,new DamageInfo(AbstractDungeon.player,amount,damageType),attackEffect));
            }
        }
        this.isDone = true;
    }
}
