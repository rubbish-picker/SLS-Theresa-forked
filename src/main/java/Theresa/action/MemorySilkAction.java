package Theresa.action;

import Theresa.card.skill.FromNight;
import Theresa.patch.SilkPatch;
import Theresa.power.buff.HatePower;
import Theresa.silk.AbstractSilk;
import Theresa.silk.MemorySilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import java.util.ArrayList;

public class MemorySilkAction extends AbstractGameAction {
    public static ArrayList<String> thirdCards = new ArrayList<>();

    public MemorySilkAction(AbstractCard card, MemorySilk silk) {
        this.c = card;
        this.silk = silk;
        if(c.dontTriggerOnUseCard)
            this.dontTrigger = true;
    }


    private boolean dontTrigger = false;
    AbstractCard c;
    MemorySilk silk;

    @Override
    public void update() {
        AbstractPlayer p  = AbstractDungeon.player;
        AbstractPower hate = p.getPower(HatePower.POWER_ID);
        if(hate!=null && hate.amount>0){
            int reduceAmount = Math.min(hate.amount,silk.isMemoried?2:3);
            if(reduceAmount == 3){
                if(c instanceof FromNight){
                    ((FromNight) c).onSpecialTrigger();
                }
                AbstractCard card = c.makeSameInstanceOf();
                if(dontTrigger){
                    card.dontTriggerOnUseCard = true;
                    card.damageTypeForTurn = DamageInfo.DamageType.THORNS;
                }
                AbstractSilk silk = SilkPatch.SilkCardField.silk.get(card);
                if(silk instanceof MemorySilk){
                    ((MemorySilk) silk).isMemoried = true;
                }
                addToTop(new PlayCardAction(card,null,true));
//                if(!thirdCards.contains(c.cardID)){
//                    thirdCards.add(c.cardID);
//                    AbstractCard card = c.makeSameInstanceOf();
//                    addToTop(new PlayCardAction(card,null,true));
//                }
//                else {
//                    addToTop(new ApplyPowerAction(p,p, new VigorPower(p,silk.amount),silk.amount));
//                }
            }
            if(reduceAmount>=2){
                addToTop(new ApplyPowerAction(p,p,new ThornsPower(p,silk.amount),silk.amount));
            }
            addToTop(new ApplyPowerAction(p, p, new MetallicizePower(p, silk.amount), silk.amount));
            addToTop(new ReducePowerAction(p,p,HatePower.POWER_ID,reduceAmount));
        }

        this.isDone = true;
    }
}
