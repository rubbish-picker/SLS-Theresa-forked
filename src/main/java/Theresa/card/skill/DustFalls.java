package Theresa.card.skill;

import Theresa.action.DustAction;
import Theresa.action.DustToPileAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.HopePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DustFalls extends AbstractTheresaCard {
    public static final String ID = "theresa:DustFalls";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public DustFalls() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.RARE,CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new HopePower(abstractPlayer,magicNumber),magicNumber));
    }

    @Override
    public void atTurnEndIfDust() {
        this.flash();
        addToBot(new DustAction(1));
        addToBot(new DustToPileAction(this, CardGroup.CardGroupType.DISCARD_PILE));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}


