import { ChangeDetectorRef, Component, inject, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import { AuthService } from "../services/auth.service";
import { ConfigService } from "../../services/config/config";
import { SharedModule } from "../../share/share-module";
import { catchError, finalize, of } from "rxjs";

@Component({
  selector: "app-login",
  standalone: true,
  imports: [TranslateModule, SharedModule],
  templateUrl: "./login.html",
  styleUrl: "./login.css",
})
export class Login implements OnInit {
  private readonly formBuilder = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly configService = inject(ConfigService);
  private readonly router = inject(Router);
  form!: FormGroup;
  isLoading = false;
  submitted = false;
  errorMessage = "";
  showPassword: boolean = false;

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.nonNullable.group({
      email: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required, Validators.minLength(6)]],
    });
  }

  //@return response()
  get f() {
    return this.form.controls;
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  onKeyMenuItem(event: KeyboardEvent): void {
    if (event.key === "Enter" || event.key === " ") {
      event.preventDefault();
    }
  }
  navigateToSignup(event: Event) {
    event.preventDefault(); // prevent form submission
    this.router.navigate(["/inscription-users"]);
  }
  //get authentication token
  submit() {
    this.submitted = true;
    if (this.form.invalid) {
      this.errorMessage = "Échec de la connexion.";
      return;
    }

    this.isLoading = true;
    this.errorMessage = "";

    const { email, password } = this.form.getRawValue();

    this.authService
      .login({ email, password })
      .pipe(
        catchError((err) => {
          this.errorMessage =
            "Échec de la connexion. Veuillez vérifier vos identifiants.";
          return of(null);
        }),
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe();
  }
}
