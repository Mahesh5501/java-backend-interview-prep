# SOLID Principles -- Easy Study & Interview Reference

A simple, practical guide to all five SOLID principles using banking and
FinFlow-style examples.

## What is SOLID?

SOLID is a set of five object-oriented design principles that help us
write software that is:

-   Easier to understand and maintain
-   Easier to extend when requirements change
-   Less tightly coupled
-   Easier to test
-   Less likely to break when we add new functionality

The five principles are:

  -----------------------------------------------------------------------
  Letter                  Principle               Main idea
  ----------------------- ----------------------- -----------------------
  S                       Single Responsibility   One class should have
                          Principle (SRP)         one responsibility

  O                       Open/Closed Principle   Extend functionality
                          (OCP)                   without unnecessarily
                                                  modifying existing code

  L                       Liskov Substitution     A child class must
                          Principle (LSP)         honor its parent's
                                                  behavior

  I                       Interface Segregation   Don't force classes to
                          Principle (ISP)         implement methods they
                                                  don't need

  D                       Dependency Inversion    Depend on abstractions,
                          Principle (DIP)         not concrete
                                                  implementations
  -----------------------------------------------------------------------

------------------------------------------------------------------------

# 1. SRP -- Single Responsibility Principle

## Simple definition

A class should have only one responsibility and one reason to change.

In simple words: **one class should handle one specific job.**

## Banking example

Imagine a `BankService` class handles all of these tasks:

-   Validating a deposit amount
-   Updating the account balance
-   Saving the transaction to the database
-   Sending an email notification

This class has too many responsibilities. If validation rules change,
database logic changes, or email behavior changes, the same class may
need modification.

That makes the class difficult to maintain and test.

## Better design

Separate the responsibilities into focused classes:

  Class                     Responsibility
  ------------------------- ----------------------------------
  `DepositValidator`        Validates the deposit amount
  `AccountService`          Updates the account balance
  `TransactionRepository`   Saves transaction information
  `NotificationService`     Sends notifications
  `BankService`             Coordinates the deposit workflow

`BankService` can coordinate the process, but the detailed work belongs
to the appropriate classes.

## Real-world backend connection

In a Spring Boot application, responsibilities are commonly separated:

-   Controller -- handles HTTP requests and responses
-   Service -- contains business logic
-   Repository -- handles data access
-   Validator -- validates input or business rules
-   Notification service -- sends notifications

SRP does **not** mean a class can have only one method. It means its
methods should belong to one cohesive responsibility.

## Interview-ready answer

"Single Responsibility Principle states that a class should have only
one responsibility and one reason to change. We follow it by separating
different responsibilities into different classes. For example, in a
banking application, validation, account balance updates, transaction
persistence, and notifications should not all be handled by one class.
This makes the code easier to maintain and test."

## Main benefit

Changes to one responsibility are less likely to affect unrelated
responsibilities.

**Remember:** SRP = Separate responsibilities.

### Main-class revision comment

> A class should have only one responsibility. It should handle one
> specific part of the application and have only one reason to change.
> For example, validation, account management, database operations, and
> notifications should be handled by separate classes.

------------------------------------------------------------------------

# 2. OCP -- Open/Closed Principle

## Simple definition

Software entities should be open for extension but closed for
modification.

In simple words: **we should be able to add new functionality without
repeatedly changing existing, working code.**

## Banking example

Imagine a bonus calculator supports these bonus types:

-   Normal -- 1%
-   Premium -- 5%
-   Employee -- 10%
-   VIP -- 15%

A less flexible design may use a long chain of `if/else` conditions
inside one method. Whenever a new bonus type is introduced, we must
modify that method.

As the number of types grows, the method becomes harder to maintain and
test.

## Better design

Create a `BonusCalculator` interface with a calculation method.

Each bonus type gets its own implementation:

-   `NormalBonus`
-   `PremiumBonus`
-   `EmployeeBonus`
-   `VipBonus`

A `BonusService` accepts the `BonusCalculator` abstraction and calls its
calculation method. It does not need to know the concrete bonus type.

When a new bonus type is introduced, create another implementation. The
existing `BonusService` can remain unchanged.

## Important Java concept: Runtime polymorphism

For example, a reference may have type `BonusCalculator`, while the
actual object is `VipBonus`.

When the method is called, Java executes the overridden method belonging
to the actual object. This is runtime polymorphism, also called dynamic
method dispatch.

## Design pattern connection

This example also demonstrates the **Strategy Pattern**:

-   `BonusCalculator` -- strategy interface
-   Individual bonus classes -- concrete strategies
-   `BonusService` -- uses a strategy

OCP does not automatically discover new implementations. Something still
needs to choose and provide the appropriate implementation. In a simple
Java example, the main method does that. In Spring Boot, dependency
injection, qualifiers, configuration, or a strategy registry can help.

## Real-world backend connection

A payment service may support UPI, card, and net banking. Each payment
type can implement a common payment interface. Adding a new payment type
should not require rewriting the core payment-processing logic.

## Interview-ready answer

"Open/Closed Principle means a class should be open for extension but
closed for modification. We should be able to introduce new behavior
without unnecessarily changing existing, tested code. For example, we
can add a new bonus type by creating another implementation of the bonus
interface instead of adding more conditions to the existing bonus
service."

## Main benefit

New functionality can be added with less risk of breaking existing
behavior.

**Remember:** OCP = Extend without modifying existing code.

### Main-class revision comment

> A class should be open for extension but closed for modification. We
> should be able to add new functionality by creating new
> implementations without changing existing, working code. For example,
> add a new bonus type by implementing `BonusCalculator` instead of
> modifying `BonusService`.

------------------------------------------------------------------------

# 3. LSP -- Liskov Substitution Principle

## Simple definition

A child class should be replaceable wherever its parent class is
expected, without breaking the application's expected behavior.

In simple words: **a child class must behave as its parent promises.**

## Banking example

Imagine a parent class called `Account` provides a `withdraw()`
operation.

There are two account types:

-   `SavingsAccount` -- supports withdrawals.
-   `FixedDepositAccount` -- does not support normal withdrawals before
    maturity.

If `FixedDepositAccount` extends `Account`, it inherits `withdraw()`.
But it cannot honor that behavior and may override the method to throw
`UnsupportedOperationException`.

Now code that expects an `Account` and calls `withdraw()` can fail when
it receives a `FixedDepositAccount`.

That is an LSP violation: the child cannot safely replace the parent
while preserving the expected behavior.

## Better design

Put only genuinely common behavior in the common `Account` abstraction.

For example:

-   `Account` -- common account behavior, such as deposit and balance
    enquiry
-   `WithdrawableAccount` -- a separate capability for accounts that
    support withdrawals
-   `SavingsAccount` -- an account that also supports
    `WithdrawableAccount`
-   `FixedDepositAccount` -- an account that does not claim to support
    normal withdrawals

This avoids promising withdrawal behavior through a parent type when not
every account can provide it.

## What LSP teaches us

-   Inheritance should represent genuine substitutability, not merely
    code reuse.
-   A subclass should honor the behavior and contract of its parent.
-   Avoid subclasses that unexpectedly reject operations promised by the
    parent.
-   Design abstractions around behavior that all their subtypes can
    correctly support.

## Interview-ready answer

"Liskov Substitution Principle states that a child class should be
usable wherever its parent class is expected without breaking the
application's expected behavior. For example, if an `Account` parent
class promises withdrawal, but `FixedDepositAccount` cannot support
withdrawal and throws an exception, it violates LSP. We should design
the abstraction so that only accounts supporting withdrawal expose that
capability."

## Main benefit

Code using a parent abstraction can work safely with its valid child
implementations.

**Remember:** LSP = Safe substitution; child must honor parent behavior.

### Main-class revision comment

> A child class should be replaceable wherever its parent class is
> expected, without breaking the application's expected behavior. Child
> classes should correctly follow the behavior and contract defined by
> their parent class. For example, if a parent `Account` supports
> withdrawal, every child class must support that behavior correctly. If
> some accounts do not support withdrawal, separate that capability into
> its own interface.

------------------------------------------------------------------------

# 4. ISP -- Interface Segregation Principle

## Simple definition

A class should not be forced to implement methods that it does not need
or support.

In simple words: **don't force a class to implement unnecessary
methods.**

## Banking example

Imagine we create one large interface called `AccountOperations` with
these methods:

-   `deposit()`
-   `withdraw()`
-   `getBalance()`
-   `calculateInterest()`

But not every account supports every operation. For example, a fixed
deposit account may not support normal withdrawals, and interest
calculation may belong to a separate component.

If every account must implement the entire interface, some classes may
end up with empty methods or methods that throw unsupported-operation
exceptions.

That is an ISP violation.

## Better design

Split the large interface into smaller, focused interfaces:

  Interface              Capability
  ---------------------- --------------------
  `Depositable`          Deposit money
  `Withdrawable`         Withdraw money
  `BalanceReadable`      Read the balance
  `InterestCalculable`   Calculate interest

Each account implements only the interfaces it actually supports.

For example:

-   `SavingsAccount` -- `Depositable`, `Withdrawable`, `BalanceReadable`
-   `FixedDepositAccount` -- `Depositable`, `BalanceReadable`,
    `InterestCalculable`

## Real-world backend connection

A payment system may have different capabilities:

-   `Payable` -- make a payment
-   `Refundable` -- process a refund
-   `StatusCheckable` -- check payment status

A payment implementation should implement only the capabilities it
supports.

## Interview-ready answer

"Interface Segregation Principle states that a class should not be
forced to implement methods it does not need. Instead of creating one
large interface with many unrelated methods, we should create smaller,
focused interfaces. For example, we can separate deposit, withdrawal,
and balance enquiry into different interfaces so each account implements
only the operations it supports."

## Main benefit

Classes depend only on the interface methods that are relevant to them.

**Remember:** ISP = Focused interfaces; don't force unnecessary methods.

### Main-class revision comment

> A class should not be forced to implement methods that it does not
> need or support. Instead of creating one large interface with many
> methods, create smaller, specific interfaces based on the capabilities
> required by different classes. For example, a Fixed Deposit account
> should not be forced to implement withdrawal functionality if it does
> not support withdrawals.

------------------------------------------------------------------------

# 5. DIP -- Dependency Inversion Principle

## Simple definition

High-level classes should not directly depend on low-level classes. Both
should depend on abstractions, such as interfaces.

In simple words: **depend on interfaces, not concrete implementations.**

## Banking example

Imagine a `PaymentService` directly creates a `UpiPayment` object and
uses it.

If we later introduce card payments or net banking, the service may need
to be modified to create and use each new concrete payment class.

This creates tight coupling between the business service and a specific
payment implementation.

## Better design

Create a common `PaymentMethod` interface.

-   `PaymentMethod` -- abstraction
-   `UpiPayment` -- implementation
-   `CardPayment` -- implementation
-   `PaymentService` -- depends on `PaymentMethod`, not directly on UPI
    or card classes

The required payment implementation is provided from outside, for
example through constructor injection.

Now `PaymentService` can work with different payment implementations
without knowing their concrete classes.

## Important distinction: DIP vs DI

These terms are related, but they are not the same.

  -----------------------------------------------------------------------
  DIP                                 DI
  ----------------------------------- -----------------------------------
  Dependency Inversion Principle      Dependency Injection

  A design principle                  A technique/pattern

  Says to depend on abstractions      Supplies dependencies from outside

  Example: service depends on         Example: constructor injection
  `PaymentMethod`                     provides `UpiPayment`
  -----------------------------------------------------------------------

Constructor injection is a common way to provide dependencies and
implement dependency inversion.

## Real-world Spring Boot connection

In Spring Boot, we commonly inject dependencies through constructors:

-   A service depends on an interface.
-   One or more Spring beans implement that interface.
-   Spring's dependency injection container provides the selected
    implementation.

If multiple implementations exist, selection can be handled with
`@Qualifier`, `@Primary`, configuration, or a strategy registry.

## Interview-ready answer

"Dependency Inversion Principle states that high-level modules should
not depend directly on low-level modules. Both should depend on
abstractions. For example, `PaymentService` should depend on a
`PaymentMethod` interface rather than directly depending on `UpiPayment`
or `CardPayment`. This reduces tight coupling and makes the application
easier to extend and test."

## Main benefit

Business logic is less dependent on implementation details, making it
easier to replace implementations and test services.

**Remember:** DIP = Depend on abstractions, not concrete classes.

### Main-class revision comment

> High-level classes should not directly depend on low-level classes.
> Both should depend on abstractions (interfaces). Abstractions should
> not depend on implementation details; implementations should depend on
> abstractions. For example, `PaymentService` should depend on a
> `PaymentMethod` interface instead of directly depending on
> `UpiPayment` or `CardPayment`.

------------------------------------------------------------------------

# 6. LSP vs ISP -- Important difference

These two principles can look similar, but they solve different
problems.

  -----------------------------------------------------------------------
  Point                   LSP                     ISP
  ----------------------- ----------------------- -----------------------
  Main focus              Inheritance and         Interface design and
                          behavior                methods

  Main question           Can a child safely      Is this class forced to
                          replace its parent?     implement methods it
                                                  doesn't need?

  Avoid                   Subclasses breaking     Large interfaces with
                          expected parent         unnecessary methods
                          behavior                

  Banking example         Fixed deposit cannot    Fixed deposit is forced
                          honor a parent's        to implement a
                          withdrawal promise      withdrawal method in a
                                                  large interface

  Memory trick            Safe substitution       Focused interfaces
  -----------------------------------------------------------------------

### Interview-ready difference

"LSP is about correct behavior in inheritance: subclasses must honor the
contracts of their parent classes. ISP is about interface design:
classes should not be forced to depend on methods they do not need."

------------------------------------------------------------------------

# 7. All five principles -- Interview revision table

  -----------------------------------------------------------------------
  Principle               Interview keywords      One-line answer
  ----------------------- ----------------------- -----------------------
  SRP                     Responsibility, reason  A class should have one
                          to change               responsibility and one
                                                  reason to change.

  OCP                     Extension, modification Software should be open
                                                  for extension but
                                                  closed for
                                                  modification.

  LSP                     Inheritance,            A child should replace
                          substitutability,       its parent without
                          behavior                breaking expected
                                                  behavior.

  ISP                     Small interfaces,       Classes should not be
                          unnecessary methods     forced to implement
                                                  methods they don't
                                                  need.

  DIP                     Abstraction, loose      High-level and
                          coupling, dependency    low-level modules
                                                  should depend on
                                                  abstractions.
  -----------------------------------------------------------------------

## Easy memory trick

-   **S -- Separate:** Separate responsibilities.
-   **O -- Open:** Open for extension, closed for modification.
-   **L -- Liskov:** Child honors parent behavior.
-   **I -- Interface:** Keep interfaces focused.
-   **D -- Depend:** Depend on abstractions.

------------------------------------------------------------------------

# 8. How SOLID works together in a real backend

Imagine we are building a FinFlow wallet application.

  -----------------------------------------------------------------------
  Principle                           How we apply it
  ----------------------------------- -----------------------------------
  SRP                                 Separate wallet validation, balance
                                      management, transaction
                                      persistence, and notifications.

  OCP                                 Add new payment or transaction
                                      strategies without repeatedly
                                      modifying core processing logic.

  LSP                                 Ensure wallet/account subtypes
                                      honor the behavior promised by
                                      their parent abstractions.

  ISP                                 Separate capabilities such as
                                      deposit, withdrawal, refund, and
                                      balance enquiry into focused
                                      interfaces.

  DIP                                 Make business services depend on
                                      repository/payment interfaces
                                      rather than concrete
                                      implementations.
  -----------------------------------------------------------------------

These principles are guidelines, not rules that require creating an
interface or class for every method. Apply them when they improve
cohesion, extensibility, substitutability, or testability.

------------------------------------------------------------------------

# 9. Common interview follow-up questions

### Q1. Why do we need SOLID principles?

They help us write maintainable, extensible, loosely coupled, and
testable object-oriented software.

### Q2. Are SOLID principles mandatory in every class?

No. They are design guidelines. Apply them when they solve a real design
problem. Avoid unnecessary abstractions and overengineering.

### Q3. Which SOLID principle helps reduce tight coupling?

DIP, because high-level and low-level modules depend on abstractions
rather than directly depending on concrete implementations.

### Q4. Which principle helps when adding new functionality?

OCP, because it encourages extension through new implementations instead
of repeatedly modifying existing code.

### Q5. Which principles are related to interfaces?

ISP focuses on keeping interfaces small and relevant. DIP encourages
depending on abstractions, commonly interfaces.

### Q6. What is the difference between SRP and ISP?

SRP focuses on the responsibility of a class. ISP focuses on the methods
exposed by an interface and the dependencies imposed on implementing
classes.

### Q7. What is the difference between OCP and DIP?

OCP focuses on extending behavior without changing existing code. DIP
focuses on structuring dependencies around abstractions. Applying DIP
often helps achieve OCP.

### Q8. Can SOLID principles be used with Spring Boot?

Yes. Spring Boot applications commonly use separated services and
repositories (SRP), interfaces and implementations (OCP/ISP),
constructor injection (DIP), and carefully designed inheritance or
composition (LSP).

------------------------------------------------------------------------

# 10. Final 60-second revision

If you have only one minute before an interview, remember this:

1.  **SRP:** One class should have one responsibility and one reason to
    change.
2.  **OCP:** Add new functionality without unnecessarily modifying
    existing code.
3.  **LSP:** A child class must honor its parent's behavior and be
    safely substitutable.
4.  **ISP:** Don't force classes to implement methods they don't need;
    use focused interfaces.
5.  **DIP:** High-level and low-level modules should depend on
    abstractions, not concrete implementations.

**Final takeaway:** SOLID is about designing software so that changes
are easier, responsibilities are clear, dependencies are flexible, and
existing behavior remains reliable.
