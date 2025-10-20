package Theresa.card.skill;

import Theresa.action.DustAction;
import Theresa.action.DustToPileAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GuiltyWillEnd extends AbstractTheresaCard {
    public static final String ID = "theresa:GuiltyWillEnd";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public GuiltyWillEnd() {
        super(ID, cardStrings.NAME, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
        dontExhaustIfExhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DustAction(magicNumber));
    }

    @Override
    public void triggerWhenLingered() {
        addToBot(new DustToPileAction(this, CardGroup.CardGroupType.HAND));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
