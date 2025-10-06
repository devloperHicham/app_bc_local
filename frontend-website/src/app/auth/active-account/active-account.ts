import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../share/share-module';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-active-account',
  imports: [SharedModule, CommonModule, FormsModule],
  templateUrl: './active-account.html',
  styleUrl: './active-account.css'
})
export class ActiveAccount implements OnInit {
  message: string = '';
  isSuccess: boolean = false;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly authService: AuthService,
    private readonly router: Router
  ) { }

  ngOnInit(): void {
    const token = this.route.snapshot.queryParamMap.get('token');

    if (token) {
      this.authService.activateAccount(token).subscribe({
        next: (res) => {
          this.isSuccess = true;
          this.message = res.message || "Votre compte a été activé avec succès.";
          setTimeout(() => this.router.navigate(['/login']), 3000);
        },
        error: (err) => {
          this.isSuccess = false;
          this.message = err.message || "Lien invalide ou expiré.";
          setTimeout(() => this.router.navigate(['/login']), 4000);
        }
      });
    } else {
      this.isSuccess = false;
      this.message = "Lien invalide.";
      setTimeout(() => this.router.navigate(['/login']), 3000);
    }
  }
}