package Theresa.action;

import Theresa.power.buff.HatePower;
import Theresa.power.buff.HopePower;
import Theresa.silk.AbstractSilk;
import Theresa.silk.MindSilk;
import Theresa.silk.NormalSilk;
import Theresa.silk.TearSilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SilkFlashAction extends AbstractGameAction {
    public SilkFlashAction(AbstractSilk silk) {
        this.silk = silk;
        this.amount = silk.amount;
    }

    @Override
    public void update() {
        if(silk instanceof NormalSilk){
            silk.card.flash();
            if(silk.card.type == AbstractCard.CardType.ATTACK)
                AbstractDungeon.actionManager.addToTop(new FastRandomDamageAction(new DamageInfo(AbstractDungeon.player,this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            else
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, this.amount,true));
        }
        else if(silk instanceof TearSilk){
            silk.card.flash();
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, this.amount,true));
            AbstractDungeon.actionManager.addToTop(new FastRandomDamageAction(new DamageInfo(AbstractDungeon.player, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        else if(silk instanceof MindSilk){
            silk.card.flash();
            AbstractPower p = null;
            AbstractPower hope = AbstractDungeon.player.getPower(HopePower.POWER_ID);
            AbstractPower hate = AbstractDungeon.player.getPower(HatePower.POWER_ID);
            int hopeAmount = 0;
            int hateAmount = 0;
            boolean isHate = false;
            if(hope!=null){
                hopeAmount = hope.amount;
            }
            if(hate!=null){
                hateAmount = hate.amount;
            }
            if(hopeAmount<hateAmount){
                isHate = false;
            }
            else if(hateAmount<hopeAmount){
                isHate = true;
            }
            else {
                isHate = AbstractDungeon.cardRandomRng.randomBoolean();
            }
            if(isHate){
                p = new HatePower(AbstractDungeon.player, 1);
            }
            else
                p = new HopePower(AbstractDungeon.player, 1);
            addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,p,1));
//            if(hope!=null&&hopeAmount>hateAmount){
//                silk.card.flash();
//                addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HatePower(AbstractDungeon.player,1),1));
//                if(hope.amount<=1)
//                    addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, HopePower.POWER_ID));
//                else
//                    addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, HopePower.POWER_ID, 1));
//            }
//            if(hate!=null&&hateAmount>hopeAmount){
//                silk.card.flash();
//                addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HopePower(AbstractDungeon.player,1),1));
//                if(hate.amount<=1)
//                    addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, HatePower.POWER_ID));
//                else
//                    addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, HatePower.POWER_ID, 1));
//            }
        }
        this.isDone = true;
    }

    AbstractSilk silk;
}
