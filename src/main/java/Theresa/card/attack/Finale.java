package Theresa.card.attack;

import Theresa.action.LongWaitAction;
import Theresa.action.TheresaAttackAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.effect.FinaleEffect;
import Theresa.patch.DustPatch;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class Finale extends AbstractTheresaCard {
    public static final String ID = "theresa:Finale";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static boolean isTest = false;

    public Finale() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.RARE,CardTarget.ALL_ENEMY);
        baseDamage = damage = 80;
        this.isMultiDamage = true;
        this.tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new TheresaAttackAction(true));
        addToBot(new VFXAction(abstractPlayer,new FinaleEffect(false),0.15F,true));
        addToBot(new VFXAction(abstractPlayer,new FinaleEffect(true),0.15F,true));
        addToBot(new LongWaitAction(0.4F));
        addToBot(new DamageAllEnemiesAction(abstractPlayer,multiDamage,damageTypeForTurn,skillEffect));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(isTest){
            return super.canUse(p,m);
        }
        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if((c.type == CardType.ATTACK ||c.type==CardType.SKILL)&&SilkPatch.SilkCardField.silk.get(c) == null)
                return false;
        }
        for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if((c.type == CardType.ATTACK ||c.type==CardType.SKILL)&&SilkPatch.SilkCardField.silk.get(c) == null)
                return false;
        }
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if((c.type == CardType.ATTACK ||c.type==CardType.SKILL)&&SilkPatch.SilkCardField.silk.get(c) == null)
                return false;
        }
        for(AbstractCard c : DustPatch.dustManager.dustCards){
            if((c.type == CardType.ATTACK ||c.type==CardType.SKILL)&&SilkPatch.SilkCardField.silk.get(c) == null)
                return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(20);
        }
    }
}



