package Theresa.action;

import Theresa.patch.DustPatch;
import Theresa.power.debuff.DyingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class LightlessAction extends AbstractGameAction {
    public LightlessAction(AbstractCreature target, int damage, DamageInfo.DamageType type, AttackEffect effect, int magic){
        this.target = target;
        this.amount = damage;
        this.magic = magic;
        this.damageType = type;
        this.attackEffect = effect;
        this.startDuration = duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DISCARD;
    }

    int magic;

    @Override
    public void update() {
        if(startDuration==duration){
            int attackTimes = 0;
            ArrayList<AbstractCard> dusts = new ArrayList<>(DustPatch.dustManager.dustCards);
            for(AbstractCard card : dusts){
                DustPatch.dustManager.removeCard(card);
                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                tmp.addToTop(card);
                tmp.moveToDiscardPile(card);
                attackTimes++;
            }
            for(int i = 0; i < attackTimes; i++){
                addToTop(new ApplyPowerAction(target,AbstractDungeon.player,new DyingPower(target,magic),magic));
            }
            for(int i = 0; i < attackTimes; i++){
                addToTop(new DamageAction(target,new DamageInfo(AbstractDungeon.player,amount,damageType),attackEffect));
            }
        }
        this.tickDuration();
    }
}

