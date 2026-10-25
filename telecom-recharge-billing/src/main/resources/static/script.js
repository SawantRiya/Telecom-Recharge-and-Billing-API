const API = "http://localhost:8080";


// =====================================
// NAVIGATION
// =====================================

function showSection(sectionName, button) {

    document.querySelectorAll(".section")
        .forEach(section => {

            section.classList.add("hidden");

        });


    document.getElementById(sectionName)
        .classList.remove("hidden");


    document.querySelectorAll(".nav-item")
        .forEach(item => {

            item.classList.remove("active");

        });


    if (button) {
        button.classList.add("active");
    }


    const titles = {

        dashboard: "Dashboard",

        customers: "Customers",

        plans: "Recharge Plans",

        recharge: "Make Recharge",

        bills: "Billing & Invoices"

    };


    document.getElementById("pageTitle")
        .innerText = titles[sectionName];


    if (sectionName === "customers") {
        loadCustomers();
    }

    if (sectionName === "plans") {
        loadPlans();
    }

    if (sectionName === "recharge") {
        loadCustomers();
        loadPlans();
    }
}


function showSectionByName(sectionName) {

    const button =
        [...document.querySelectorAll(".nav-item")]
            .find(item =>
                item.getAttribute("onclick")
                    ?.includes("'" + sectionName + "'")
            );

    showSection(sectionName, button);
}


// =====================================
// TOAST
// =====================================

function showToast(message) {

    const toast =
        document.getElementById("toast");

    document.getElementById("toastMessage")
        .innerText = message;

    toast.classList.add("show");


    setTimeout(() => {

        toast.classList.remove("show");

    }, 3000);
}


// =====================================
// CUSTOMERS
// =====================================

async function loadCustomers() {

    try {

        const response =
            await fetch(API + "/api/customers");


        if (!response.ok) {
            throw new Error("Unable to load customers");
        }


        const customers =
            await response.json();


        const table =
            document.getElementById("customerTable");


        const select =
            document.getElementById("customerSelect");


        table.innerHTML = "";

        select.innerHTML =
            `<option value="">Select customer</option>`;


        customers.forEach(customer => {

            table.innerHTML += `

                <tr>

                    <td>
                        #${customer.customerId}
                    </td>

                    <td>
                        <strong>
                            ${customer.name}
                        </strong>
                    </td>

                    <td>
                        ${customer.mobileNumber}
                    </td>

                    <td>
                        ${customer.email || "-"}
                    </td>

                </tr>

            `;


            select.innerHTML += `

                <option value="${customer.customerId}">

                    ${customer.name}
                    -
                    ${customer.mobileNumber}

                </option>

            `;

        });


        document.getElementById("customerCount")
            .innerText = customers.length;


    } catch (error) {

        console.error(error);

        showToast("Unable to load customers");

    }
}


// =====================================
// ADD CUSTOMER
// =====================================

async function addCustomer() {

    const name =
        document.getElementById("customerName").value.trim();

    const mobile =
        document.getElementById("customerMobile").value.trim();

    const email =
        document.getElementById("customerEmail").value.trim();


    if (!name || !mobile) {

        showToast("Please enter name and mobile number");

        return;
    }


    const customer = {

        name: name,

        mobileNumber: mobile,

        email: email

    };


    try {

        const response = await fetch(
            API + "/api/customers",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(customer)
            }
        );


        if (!response.ok) {

            const error =
                await response.text();

            throw new Error(error);

        }


        document.getElementById("customerName")
            .value = "";

        document.getElementById("customerMobile")
            .value = "";

        document.getElementById("customerEmail")
            .value = "";


        showToast("Customer added successfully");

        loadCustomers();


    } catch (error) {

        console.error(error);

        showToast("Unable to add customer");

    }
}


// =====================================
// PLANS
// =====================================

async function loadPlans() {

    try {

        const response =
            await fetch(API + "/api/plans");


        if (!response.ok) {
            throw new Error("Unable to load plans");
        }


        const plans =
            await response.json();


        const table =
            document.getElementById("planTable");


        const select =
            document.getElementById("planSelect");


        const container =
            document.getElementById("plansContainer");


        table.innerHTML = "";

        container.innerHTML = "";

        select.innerHTML =
            `<option value="">Select recharge plan</option>`;


        plans.forEach(plan => {


            // TABLE

            table.innerHTML += `

                <tr>

                    <td>
                        #${plan.planId}
                    </td>

                    <td>
                        <strong>
                            ${plan.planName}
                        </strong>
                    </td>

                    <td>
                        ₹${plan.amount}
                    </td>

                    <td>
                        ${plan.validityDays} days
                    </td>

                    <td>
                        ${plan.dataGb} GB
                    </td>

                    <td>
                        ${plan.voiceMinutes} min
                    </td>

                    <td>
                        ${plan.smsCount}
                    </td>

                </tr>

            `;


            // PLAN CARD

            container.innerHTML += `

                <div class="plan-card">

                    <div class="plan-name">
                        ${plan.planName}
                    </div>

                    <div class="plan-price">
                        ₹${plan.amount}
                        <small>/ plan</small>
                    </div>

                    <div class="plan-details">

                        <span>
                            📅 ${plan.validityDays} Days
                        </span>

                        <span>
                            📶 ${plan.dataGb} GB
                        </span>

                        <span>
                            📞 ${plan.voiceMinutes} Min
                        </span>

                        <span>
                            💬 ${plan.smsCount} SMS
                        </span>

                    </div>

                </div>

            `;


            // RECHARGE SELECT

            select.innerHTML += `

                <option value="${plan.planId}">

                    ${plan.planName}
                    - ₹${plan.amount}

                </option>

            `;

        });


        document.getElementById("planCount")
            .innerText = plans.length;


    } catch (error) {

        console.error(error);

        showToast("Unable to load plans");

    }
}


// =====================================
// ADD PLAN
// =====================================

async function addPlan() {

    const plan = {

        planName:
            document.getElementById("planName")
                .value.trim(),

        amount:
            parseFloat(
                document.getElementById("planAmount")
                    .value
            ),

        validityDays:
            parseInt(
                document.getElementById("planValidity")
                    .value
            ),

        dataGb:
            parseFloat(
                document.getElementById("planData")
                    .value
            ),

        voiceMinutes:
            parseInt(
                document.getElementById("planVoice")
                    .value
            ),

        smsCount:
            parseInt(
                document.getElementById("planSms")
                    .value
            )

    };


    if (!plan.planName ||
        isNaN(plan.amount) ||
        isNaN(plan.validityDays)) {

        showToast("Please enter plan details");

        return;
    }


    try {

        const response = await fetch(
            API + "/api/plans",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(plan)
            }
        );


        if (!response.ok) {

            throw new Error("Plan creation failed");

        }


        document.getElementById("planName").value = "";
        document.getElementById("planAmount").value = "";
        document.getElementById("planValidity").value = "";
        document.getElementById("planData").value = "";
        document.getElementById("planVoice").value = "";
        document.getElementById("planSms").value = "";


        showToast("Recharge plan created successfully");

        loadPlans();


    } catch (error) {

        console.error(error);

        showToast("Unable to create plan");

    }
}


// =====================================
// MAKE RECHARGE
// =====================================

async function makeRecharge() {

    const customerId =
        document.getElementById("customerSelect").value;


    const planId =
        document.getElementById("planSelect").value;


    if (!customerId || !planId) {

        showToast(
            "Please select customer and recharge plan"
        );

        return;
    }


    const request = {

        customerId: parseInt(customerId),

        planId: parseInt(planId)

    };


    try {

        const response = await fetch(
            API + "/api/recharges",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(request)

            }
        );


        if (!response.ok) {

            const error =
                await response.text();

            throw new Error(error);

        }


        const result =
            await response.json();


        showToast("Recharge completed successfully");


        document.getElementById("rechargeResult")
            .innerHTML = `

                <div class="receipt">

                    <div class="receipt-header">

                        <h3>
                            <i class="fa-solid fa-circle-check"></i>
                            Recharge Successful
                        </h3>

                        <p>
                            Transaction completed successfully
                        </p>

                    </div>


                    <div class="receipt-body">

                        <div class="receipt-row">

                            <span>Recharge ID</span>

                            <strong>
                                #${result.rechargeId}
                            </strong>

                        </div>


                        <div class="receipt-row">

                            <span>Bill ID</span>

                            <strong>
                                #${result.billId}
                            </strong>

                        </div>


                        <div class="receipt-row">

                            <span>Mobile Number</span>

                            <strong>
                                ${result.mobileNumber}
                            </strong>

                        </div>


                        <div class="receipt-row">

                            <span>Recharge Plan</span>

                            <strong>
                                ${result.planName}
                            </strong>

                        </div>


                        <div class="receipt-row">

                            <span>Recharge Amount</span>

                            <strong>
                                ₹${Number(result.rechargeAmount).toFixed(2)}
                            </strong>

                        </div>


                        <div class="receipt-row">

                            <span>Tax</span>

                            <strong>
                                ₹${Number(result.tax).toFixed(2)}
                            </strong>

                        </div>


                        <div class="receipt-total">

                            <span>
                                Total Amount
                            </span>

                            <strong>
                                ₹${Number(result.totalAmount).toFixed(2)}
                            </strong>

                        </div>

                    </div>

                </div>

            `;


    } catch (error) {

        console.error(error);


        document.getElementById("rechargeResult")
            .innerHTML = `

                <div class="empty-state">

                    <div class="empty-icon"
                         style="color:#dc2626;background:#fef2f2;">

                        <i class="fa-solid fa-circle-exclamation"></i>

                    </div>

                    <h3>
                        Recharge Failed
                    </h3>

                    <p>
                        ${error.message}
                    </p>

                </div>

            `;

    }
}


// =====================================
// GET BILL
// =====================================

async function getBill() {

    const billId =
        document.getElementById("billId").value;


    if (!billId) {

        showToast("Please enter a Bill ID");

        return;
    }


    try {

        const response =
            await fetch(
                API + "/api/bills/" + billId
            );


        if (!response.ok) {

            throw new Error("Bill not found");

        }


        const bill =
            await response.json();


        document.getElementById("billResult")
            .innerHTML = `

                <div class="invoice">

                    <div class="invoice-top">

                        <div class="invoice-brand">

                            <div class="invoice-brand-icon">

                                <i class="fa-solid fa-signal"></i>

                            </div>

                            <div>

                                <h3>
                                    Telecom
                                </h3>

                                <span>
                                    Recharge & Billing
                                </span>

                            </div>

                        </div>


                        <div class="invoice-meta">

                            <strong>
                                INVOICE
                            </strong>

                            <br>

                            Bill #${bill.billId}

                        </div>

                    </div>


                    <div class="invoice-content">

                        <div class="invoice-row">

                            <span>
                                Customer ID
                            </span>

                            <strong>
                                #${bill.customerId}
                            </strong>

                        </div>


                        <div class="invoice-row">

                            <span>
                                Recharge ID
                            </span>

                            <strong>
                                #${bill.rechargeId}
                            </strong>

                        </div>


                        <div class="invoice-row">

                            <span>
                                Recharge Amount
                            </span>

                            <strong>
                                ₹${Number(bill.amount).toFixed(2)}
                            </strong>

                        </div>


                        <div class="invoice-row">

                            <span>
                                Tax
                            </span>

                            <strong>
                                ₹${Number(bill.tax).toFixed(2)}
                            </strong>

                        </div>


                        <div class="invoice-total">

                            <span>
                                Total Amount
                            </span>

                            <strong>
                                ₹${Number(bill.totalAmount).toFixed(2)}
                            </strong>

                        </div>

                    </div>

                </div>

            `;


        showToast("Bill loaded successfully");


    } catch (error) {

        document.getElementById("billResult")
            .innerHTML = `

                <div class="empty-state">

                    <div class="empty-icon">

                        <i class="fa-solid fa-file-circle-xmark"></i>

                    </div>

                    <h3>
                        Bill Not Found
                    </h3>

                    <p>
                        Please check the Bill ID and try again.
                    </p>

                </div>

            `;

    }
}


// =====================================
// INITIAL LOAD
// =====================================

window.addEventListener("load", () => {

    loadCustomers();

    loadPlans();

});