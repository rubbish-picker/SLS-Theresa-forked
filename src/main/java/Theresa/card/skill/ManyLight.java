package Theresa.card.skill;

import Theresa.action.ChooseDustToPileAction;
import Theresa.action.ManyLightAction;
import Theresa.action.OnlyForYouAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ManyLight extends AbstractTheresaCard {
    public static final String ID = "theresa:ManyLight";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ManyLight() {
        super(ID, cardStrings.NAME, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        exhaust = true;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ManyLightAction());
        addToBot(new ChooseDustToPileAction(CardGroup.CardGroupType.HAND,magicNumber).setAnyNumber());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}


