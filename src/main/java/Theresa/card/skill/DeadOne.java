package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DeadOne extends AbstractTheresaCard {
    public static final String ID = "theresa:DeadOne";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public DeadOne() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        baseBlock = block = 8;
        baseMagicNumber = magicNumber = 8;
        exhaustAfterBlockDamage = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int randomBlock = com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng.random(0, this.block*2);
        addToBot(new GainBlockAction(abstractPlayer, randomBlock));
    }

    @Override
    public int blockDamageIfDust() {
        return magicNumber;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeMagicNumber(3);
        }
    }
}



