package Theresa.card.skill;

import Theresa.action.DustToPileAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DustDefend extends AbstractTheresaCard {
    public static final String ID = "theresa:DustDefend";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public DustDefend() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.BASIC,CardTarget.SELF);
        baseBlock = block = 3;
        isInnate = true;
        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int randomBlock = com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng.random(0, this.block*2);
        addToBot(new GainBlockAction(abstractPlayer,randomBlock));
    }

    @Override
    public void triggerWhenLingered() {
        addToBot(new DustToPileAction(this, CardGroup.CardGroupType.HAND));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBlock(2);
        }
    }
}
