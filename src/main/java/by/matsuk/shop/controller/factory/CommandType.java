package by.matsuk.shop.controller.factory;

import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.controller.command.impl.admin.*;
import by.matsuk.shop.controller.command.impl.client.*;
import by.matsuk.shop.controller.command.impl.common.*;

import java.util.Optional;

/**
 * The enum Command type.
 */
public enum CommandType {
    /**
     * The Insert new product.
     */
    INSERT_NEW_PRODUCT(new InsertNewProductCommand()),
    /**
     * The Sign in.
     */
    SIGN_IN(new SignInCommand()),
    /**
     * The Sign-out.
     */
    SIGN_OUT(new SignOutCommand()),
    /**
     * The Change language.
     */
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    /**
     * The Registration.
     */
    REGISTRATION(new RegistrationCommand()),
    /**
     * The Find all menu.
     */
    FIND_ALL_POSTCARD(new FindAllPostcardCommand()),
    /**
     * The Find all users.
     */
    FIND_ALL_USERS(new FindAllUsersCommand()),
    /**
     * To Delete user.
     */
    DELETE_USER(new DeleteAdminCommand()),
    /**
     * The Upload product photo.
     */
    UPLOAD_PRODUCT_PHOTO(new UploadProductPhotoCommand()),
    /**
     * The Update user profile.
     */
    UPDATE_USER_PROFILE(new UpdateUserProfileCommand()),
    /**
     * The Change password.
     */
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    /**
     * The Go-to update product page.
     */
    GO_TO_UPDATE_PRODUCT_PAGE(new GoToUpdateProductPageCommand()),
    /**
     * To Delete product.
     */
    DELETE_PRODUCT(new DeleteProductCommand()),
    /**
     * The Block user.
     */
    BLOCK_USER(new BlockUserByIdCommand()),
    /**
     * To Unblock user.
     */
    UNBLOCK_USER(new UnblockUserByIdCommand()),
    /**
     * The Creation order.
     */
    CREATE_ORDER(new CreateOrderCommand()),
    /**
     * The Add product to cart.
     */
    ADD_PRODUCT_TO_CART(new AddProductToCartCommand()),
    /**
     * The Go to basket page.
     */
    GO_TO_BASKET_PAGE(new GoToBasketPageCommand()),
    /**
     * The Update product.
     */
    UPDATE_PRODUCT(new UpdateProductCommand()),
    /**
     * To Delete product in basket.
     */
    DELETE_PRODUCT_IN_BASKET(new DeleteProductInBasketCommand()),
    /**
     * To Calculate user discount.
     */
    CALCULATE_USER_DISCOUNT(new CalculateUserDiscountCommand()),
    /**
     * The Go-to orders page.
     */
    GO_TO_ORDERS_PAGE(new GoToOrdersPageCommand()),
    /**
     * The Find all orders.
     */
    FIND_ALL_ORDERS(new FindAllOrdersCommand()),
    /**
     * The Change order state.
     */
    CHANGE_ORDER_STATE(new ChangeOrderStateCommand()),
    /**
     * The Find all menu by section.
     */
    FIND_ALL_POSTCARD_BY_SECTION(new FindAllPostcardBySectionCommand()),
    /**
     * The Insert new section.
     */
    INSERT_NEW_SECTION(new InsertNewSectionCommand()),
    /**
     * The Change section name.
     */
    CHANGE_SECTION_NAME(new UpdateSectionNameCommand()),
    /**
     * The Delete section.
     */
    DELETE_SECTION(new DeleteSectionCommand()),
    /**
     * The Find all admins.
     */
    FIND_ALL_ADMINS(new FindAllAdminsCommand()),
    /**
     * The Sort all menu by price.
     */
    SORT_ALL_POSTCARDS_BY_PRICE(new SortAllPostcardsByPriceCommand()),
    /**
     * The Find all removing products.
     */
    FIND_ALL_REMOVING_PRODUCTS(new FindAllRemovingProductsCommand()),
    /**
     * The Find all removing sections.
     */
    FIND_ALL_REMOVING_SECTIONS(new FindAllRemovingSectionsCommand()),
    /**
     * The Restore menu product.
     */
    RESTORE_MENU_PRODUCT(new RestorePostcardProductCommand()),
    /**
     * The Restore section.
     */
    RESTORE_SECTION(new RestoreSectionCommand()),

    /**
     * The Sort all menu by popularity.
     */
    SORT_ALL_POSTCARDS_BY_POPULARITY(new SortAllPostcardsByPopularityCommand()),

    /**
     * The settings page.
     */
    GO_TO_SETTINGS(new GoToSettingsCommand());
    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    /**
     * Get command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Provide command optional.
     *
     * @param command the command
     * @return the optional
     */
    public static Optional<Command> provideCommand(String command) {
        Optional<Command> resultCommand;
        if (command == null || command.isEmpty()) {
            return Optional.empty();
        }
        try {
            CommandType commandType = CommandType.valueOf(command.toUpperCase());
            resultCommand = Optional.of(commandType.getCommand());
        } catch (IllegalArgumentException exp) {
            resultCommand = Optional.empty();
        }
        return resultCommand;
    }
}