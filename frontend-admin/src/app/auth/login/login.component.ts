import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { ConfigService } from '../../services/config/config.service';
import { SharedModule } from '../../share/shared.module';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  private readonly formBuilder = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly configService = inject(ConfigService);
  private readonly router = inject(Router);
  form!: FormGroup;
  isLoading = false;
  submitted = false;
  errorMessage = '';

  ngOnInit(): void {
    this.form = this.formBuilder.nonNullable.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  //@return response()
  get f() {
    return this.form.controls;
  }

  //get authentication token
  submit() {
    this.submitted = true;
    if (this.form.invalid) {
      this.errorMessage = 'Échec de la connexion.';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    const { email, password } = this.form.getRawValue();

    this.authService
      .login({ email, password })
      .pipe(
        catchError((err) => {
          this.errorMessage =
            'Échec de la connexion. Veuillez vérifier vos identifiants.';
          return of(null);
        }),
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe();
  }
}
