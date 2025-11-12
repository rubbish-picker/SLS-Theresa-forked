package Theresa.card.special;

import Theresa.helper.StringHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EasyMode extends CustomCard {
    public static final String ID = "theresa:EasyMode";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public EasyMode() {
        super(ID,cardStrings.NAME, StringHelper.getCardIMGPath(ID, CardType.SKILL),-2, cardStrings.DESCRIPTION,CardType.SKILL,CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {

    }
}
